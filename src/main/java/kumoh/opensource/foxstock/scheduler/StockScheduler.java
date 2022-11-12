package kumoh.opensource.foxstock.scheduler;

import kumoh.opensource.foxstock.api.CodeApi;
import kumoh.opensource.foxstock.api.FinaStatApi;
import kumoh.opensource.foxstock.api.PriceApi;
import kumoh.opensource.foxstock.api.dto.CodeDto;
import kumoh.opensource.foxstock.api.dto.FinaStatDto;
import kumoh.opensource.foxstock.api.dto.PriceDto;
import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import kumoh.opensource.foxstock.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Slf4j
public class StockScheduler {

    private final CodeApi codeApi;
    private final FinaStatApi finaStatApi;
    private final PriceApi priceApi;
    private final StockRepository stockRepository;


    //@Scheduled
//    public List<Stock> dailyUpdate(){
//        List<PriceDto> prices = priceApi.getPriceBySrtnCd()
//
//        List<Stock> stocks = stockRepository.findAll();
//        stocks.forEach(stock -> {
//            String srtnCd = stock.getSrtnCd();
//
//            int currentPrice = Integer.parseInt(prices.get(srtnCd).getClpr());
//            long numberOfStock = Long.parseLong(prices.get(srtnCd).getLstgStCnt());
//
//            stock.setCurrentPrice(currentPrice);
//            stock.setLstgStCnt(numberOfStock);
//        });
//        return stocks;
//    }

    //@Scheduled
    public List<Stock> yearlyUpdate(){
        codeApi.saveAllCode();;
        priceApi.saveAllPrice();
        finaStatApi.saveAllFinaStatDto();

        List<CodeDto> codeDtos = codeApi.getAllCode();
        //crno

        List<Stock> stocks = new ArrayList<>();

        codeDtos.forEach(codeDto -> {
            Stock stock = new Stock();

            stock.setSrtnCd( codeDto.getSrtnCd() );
            stock.setName( codeDto.getItmsNm() );
            stock.setCrno( codeDto.getCrno() );

            stocks.add(stock);
        });

        stocks.forEach(stock -> {
            String srtnCd = stock.getSrtnCd();
            PriceDto priceDto = priceApi.getPriceBySrtnCd(srtnCd);

            stock.setCurrentPrice(Integer.parseInt(priceDto.getClpr()));
            stock.setLstgStCnt(Long.parseLong(priceDto.getLstgStCnt()));

        });

        stocks.forEach(stock -> {
            String crno = stock.getCrno();
            List<FinaStatDto> finaStatByCrno = finaStatApi.getFinaStatByCrno(crno);
            log.info("finaStatByCrno = {}",finaStatByCrno);

            setFirstCapital(stock,finaStatByCrno);
            setBps(stock);
            setPbr(stock);
            setAverageRoe(stock, finaStatByCrno);
            setExpectedReturn(stock);
            setPurchasePrice(stock);
        });


        stockRepository.saveAll(stocks);
        return stocks;

    }

    private void setFirstCapital(Stock stock, List<FinaStatDto> finaStatDtos) {
        int currentYear = LocalDate.now().getYear();
        String firstYear = Integer.toString(currentYear-1);

        Optional<FinaStatDto> firstFinaStat = finaStatDtos.stream()
                .filter(finaStatDto -> finaStatDto.getBizYear().equals(firstYear))
                .filter(finaStatDto -> finaStatDto.getFnclDcdNm().contains("연결"))
                .findFirst();

        if(firstFinaStat.isEmpty()){
            firstFinaStat = finaStatDtos.stream()
                    .filter(finaStatDto -> finaStatDto.getBizYear().equals(firstYear))
                    .filter(finaStatDto -> finaStatDto.getFnclDcdNm().contains("별도"))
                    .findFirst();
        }

        Long firstCapital = firstFinaStat.map(finaStatDto -> Long.parseLong(finaStatDto.getEnpTcptAmt())).orElse(-1L);

        stock.setCapital(firstCapital);
    }

    private void setBps(Stock stock) {
        stock.setBps((int)(stock.getCapital() / stock.getLstgStCnt()));
    }

    private void setPbr(Stock stock) {
        Double pbr = (double) stock.getCurrentPrice() / stock.getBps();
        if(pbr.isInfinite()){
            stock.setPbr(-1.0);
        }
        else{
            stock.setPbr(pbr);
        }

    }


    private void setAverageRoe(Stock stock, List<FinaStatDto> finaStatDtos){
        int currentYear = LocalDate.now().getYear();
        String firstYear = Integer.toString(currentYear-1);
        String secondYear = Integer.toString(currentYear-2);
        String thirdYear = Integer.toString(currentYear-3);

        List<FinaStatDto> finaStats = finaStatDtos.stream()
                .filter(finaStatDto -> finaStatDto.getFnclDcdNm().contains("연결")).toList();

        if(finaStats.isEmpty()){
            finaStats = finaStatDtos.stream()
                    .filter(finaStatDto -> finaStatDto.getFnclDcdNm().contains("별도")).toList();
        }

        Optional<FinaStatDto> firstFinaStat = finaStats.stream()
                .filter(finaStatDto -> finaStatDto.getBizYear().equals(firstYear)).findFirst();
        Optional<FinaStatDto> secondFinaStat = finaStats.stream()
                .filter(finaStatDto -> finaStatDto.getBizYear().equals(secondYear)).findFirst();
        Optional<FinaStatDto> thirdFinaStat = finaStats.stream()
                .filter(finaStatDto -> finaStatDto.getBizYear().equals(thirdYear)).findFirst();


        String crno = stock.getCrno();


        long firstCapital = Long.parseLong(firstFinaStat.map(FinaStatDto::getEnpTcptAmt).orElse("-1"));
        long secondCapital = Long.parseLong(secondFinaStat.map(FinaStatDto::getEnpTcptAmt).orElse("-1"));
        long thirdCapital = Long.parseLong(thirdFinaStat.map(FinaStatDto::getEnpTcptAmt).orElse("-1"));

        long firstProfit = Long.parseLong(firstFinaStat.map(FinaStatDto::getEnpCrtmNpf).orElse("0"));
        long secondProfit = Long.parseLong(secondFinaStat.map(FinaStatDto::getEnpCrtmNpf).orElse("0"));
        long thirdProfit = Long.parseLong(thirdFinaStat.map(FinaStatDto::getEnpCrtmNpf).orElse("0"));

        double firstRoe = (double)firstProfit/firstCapital;
        double secondRoe = (double)secondProfit/secondCapital;
        double thirdRoe = (double)thirdProfit/thirdCapital;

        stock.setAverageRoe((firstRoe + secondRoe + thirdRoe) / 3 + 1);

    }

    private void setExpectedReturn(Stock stock){
        Double expectedReturn = stock.getAverageRoe() / Math.pow(stock.getPbr(), 0.1 );

        if(expectedReturn.isNaN()){
            stock.setExpectedReturn(0.0);
        }
        else{
            stock.setExpectedReturn(expectedReturn);
        }
    }

    private void setPurchasePrice(Stock stock){
        stock.setPurchasePrice((int)(stock.getBps() * Math.pow(stock.getAverageRoe(), 10) / Math.pow(1.15, 10)));
    }

}

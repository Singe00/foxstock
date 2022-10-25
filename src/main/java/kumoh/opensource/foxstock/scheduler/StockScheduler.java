package kumoh.opensource.foxstock.scheduler;

import kumoh.opensource.foxstock.api.CodeAPI;
import kumoh.opensource.foxstock.api.FinaStatAPI;
import kumoh.opensource.foxstock.api.PriceAPI;
import kumoh.opensource.foxstock.api.dto.CodeDto;
import kumoh.opensource.foxstock.api.dto.FinaStatDto;
import kumoh.opensource.foxstock.api.dto.PriceDto;
import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import kumoh.opensource.foxstock.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class StockScheduler {

    private final CodeAPI codeAPI;
    private final FinaStatAPI finaStatAPI;
    private final PriceAPI priceAPI;
    private final StockRepository stockRepository;

    //@Scheduled
    public List<Stock> dailyUpdate() throws IOException, ParseException {
        Map<String, PriceDto> prices = priceAPI.getAllPrice();

        List<Stock> stocks = stockRepository.findAll();
        stocks.forEach(stock -> {
            String srtnCd = stock.getSrtnCd();

            int currentPrice = Integer.parseInt(prices.get(srtnCd).getClpr());
            long numberOfStock = Long.parseLong(prices.get(srtnCd).getLstgStCnt());

            stock.setCurrentPrice(currentPrice);
            stock.setLstgStCnt(numberOfStock);
        });
        return stocks;
    }

    //@Scheduled
    public List<Stock> yearlyUpdate() throws IOException, ParseException {
        Map<String, CodeDto> codes = codeAPI.getAllCode();
        //crno
        Map<String, PriceDto> prices = priceAPI.getAllPrice();
        //srtnCd
        Map<String, Map<String, FinaStatDto>> finaStats = finaStatAPI.getAllFinaStat();
        //bizYear       //crno


        List<CodeDto> codeDtos = codes.values().stream().toList();
        List<Stock> stocks = new ArrayList<>();

        codeDtos.forEach(codeDto -> {
            String srtnCd = codeDto.getSrtnCd();
            String itmsNm = codeDto.getItmsNm();
            String crno = codeDto.getCrno();

            Stock stock = new Stock();
            stock.setSrtnCd(srtnCd);
            stock.setName(itmsNm);
            stock.setCrno(crno);

            stocks.add(stock);
        });

        stocks.forEach(stock -> {
            PriceDto priceDto = prices.get(stock.getSrtnCd());
            int currentPrice = Integer.parseInt(priceDto.getClpr());
            long numberOfStock =  Long.parseLong(priceDto.getLstgStCnt());
            Double expectedReturn = calculateExpectedReturn(stock, finaStats);
            Integer purchasePrice = calculatePurchasePrice(stock, finaStats);


            stock.setCurrentPrice(currentPrice);
            stock.setLstgStCnt(numberOfStock);
            stock.setExpectedReturn(expectedReturn);
            stock.setPurchasePrice(purchasePrice);

        });

        return stocks;

    }

    public Stock oneUpdateByItmsNm(String itmsNm) throws ParseException, IOException {
        Map<String, CodeDto> code = codeAPI.getCodeByItmsNm(itmsNm);
        CodeDto codeDto = code.values().stream().findFirst().get();

        Map<String, Map<String, FinaStatDto>> finaStatByCrno = finaStatAPI.getFinaStatByCrno(codeDto.getCrno());

        Map<String, PriceDto> priceByItmsNm = priceAPI.getPriceByItmsNm(itmsNm);
        PriceDto priceDto = priceByItmsNm.values().stream().findFirst().get();

        String srtnCd = codeDto.getSrtnCd();
        String name = codeDto.getItmsNm();
        String crno = codeDto.getCrno();

        Stock stock = new Stock();
        stock.setSrtnCd(srtnCd);
        stock.setName(name);
        stock.setCrno(crno);
        stock.setCurrentPrice(Integer.parseInt(priceDto.getClpr()));
        stock.setLstgStCnt(Long.parseLong(priceDto.getLstgStCnt()));
        stock.setExpectedReturn(calculateExpectedReturn(stock, finaStatByCrno));
        stock.setPurchasePrice(calculatePurchasePrice(stock,finaStatByCrno));

        return stock;
    }



    private Double calculateExpectedReturn(Stock stock, Map<String, Map<String, FinaStatDto>> finaStats){
        String crno = stock.getCrno();
        Map<String, FinaStatDto> firstYear = finaStats.get("firstYear");
        Optional<FinaStatDto> finaStatDto = Optional.ofNullable(firstYear.get(crno));
        if(finaStatDto.isEmpty()){
            return null;
        }

        long firstCapital = Long.parseLong(finaStatDto.get().getEnpTcptAmt());
        Double averageRoe = calculateAverageRoe(stock, finaStats);
        if(averageRoe == null){
            return null;
        }
        double pbr = (double)stock.getCurrentPrice() * stock.getLstgStCnt() / firstCapital; // 시가총액 / 자본

        return averageRoe / Math.pow(pbr, 0.1 );
    }

    private Integer calculatePurchasePrice(Stock stock, Map<String, Map<String, FinaStatDto>> finaStats){
        String crno = stock.getCrno();
        Map<String, FinaStatDto> firstYear = finaStats.get("firstYear");
        Optional<FinaStatDto> finaStatDto = Optional.ofNullable(firstYear.get(crno));
        if(finaStatDto.isEmpty()){
            return null;
        }
        long firstCapital = Long.parseLong(finaStatDto.get().getEnpTcptAmt());
        Double averageRoe = calculateAverageRoe(stock, finaStats);
        if(averageRoe == null){
            return null;
        }

        if(stock.getLstgStCnt() == 0){
            return null;
        }
        long bps = firstCapital / stock.getLstgStCnt(); // 자본/주식수

        return (int)(bps * Math.pow(averageRoe, 10) / Math.pow(1.15, 10));
    }

    private Double calculateAverageRoe(Stock stock, Map<String, Map<String, FinaStatDto>> finaStats){
        Map<String, FinaStatDto> firstYear = finaStats.get("firstYear");
        Map<String, FinaStatDto> secondYear = finaStats.get("secondYear");
        Map<String, FinaStatDto> thirdYear = finaStats.get("thirdYear");

        String crno = stock.getCrno();

        Optional<FinaStatDto> firstFinaDto = Optional.ofNullable(firstYear.get(crno));
        Optional<FinaStatDto> secondFinaDto = Optional.ofNullable(secondYear.get(crno));
        Optional<FinaStatDto> thirdFinaDto = Optional.ofNullable(thirdYear.get(crno));

        if(firstFinaDto.isEmpty() || secondFinaDto.isEmpty() || thirdFinaDto.isEmpty()){
            return null;
        }

        long firstCapital = Long.parseLong(firstFinaDto.get().getEnpTcptAmt());
        long secondCapital = Long.parseLong(secondFinaDto.get().getEnpTcptAmt());
        long thirdCapital = Long.parseLong(thirdFinaDto.get().getEnpTcptAmt());

        long firstProfit = Long.parseLong(firstFinaDto.get().getEnpCrtmNpf());
        long secondProfit = Long.parseLong(secondFinaDto.get().getEnpCrtmNpf());
        long thirdProfit = Long.parseLong(thirdFinaDto.get().getEnpCrtmNpf());

        double firstRoe = (double)firstProfit/firstCapital;
        double secondRoe = (double)secondProfit/secondCapital;
        double thirdRoe = (double)thirdProfit/thirdCapital;

        return (firstRoe + secondRoe + thirdRoe) / 3 + 1;
    }

}

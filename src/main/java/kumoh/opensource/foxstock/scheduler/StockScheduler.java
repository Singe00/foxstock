package kumoh.opensource.foxstock.scheduler;

import kumoh.opensource.foxstock.api.*;
import kumoh.opensource.foxstock.api.dto.CodeDto;
import kumoh.opensource.foxstock.api.dto.OpenDartFinaStatDto;
import kumoh.opensource.foxstock.api.dto.PriceDto;
import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import kumoh.opensource.foxstock.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
@Slf4j
public class StockScheduler {

    private final CodeApi codeApi;
    private final OpenDartCodeApi openDartCodeApi;
    private final OpenDartFinaStatApi openDartFinaStatApi;
    private final PriceApi priceApi;
    private final StockRepository stockRepository;


    @Scheduled(cron = " 0 0 5 * * * ")
    public void dailyUpdate(){
        priceApi.saveAllPrice();

        List<Stock> stocks = stockRepository.findAll();
        setPrice(stocks);
        stocks.forEach(stock -> {
            setExpectedReturn(stock);
            setPurchasePrice(stock);
            stockRepository.save(stock);
        });

    }

    //@Scheduled
    public List<Stock> yearlyUpdate(){
        codeApi.saveAllCode();
        priceApi.saveAllPrice();
        openDartCodeApi.parsingXml();
        openDartFinaStatApi.deleteAllFinaStat();

        List<Stock> stocks = setCode();
        setPrice(stocks);
        setFinaStat(stocks);

        stockRepository.deleteAllByMrktCtg("KONEX");

        return stocks;
    }


    public void subUpdate(){
        List<Stock> stocks = stockRepository.findAllByCapital(-1L);
        setFinaStat(stocks);

    }

    private List<Stock> setCode(){
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

        return stocks;
    }

    private void setPrice(List<Stock> stocks){
        stocks.forEach(stock -> {
            String srtnCd = stock.getSrtnCd();
            PriceDto priceDto = priceApi.getPriceBySrtnCd(srtnCd);

            stock.setCurrentPrice(Integer.parseInt(priceDto.getClpr()));
            stock.setLstgStCnt(Long.parseLong(priceDto.getLstgStCnt()));
            stock.setMrktCtg(priceDto.getMrktCtg());

            stockRepository.save(stock);
        });
    }

    private void setFinaStat(List<Stock> stocks){
        stocks.forEach(stock -> {
            String srtnCd = stock.getSrtnCd();
            openDartFinaStatApi.saveOpenDartFinaStatBySrtnCd(srtnCd);
            OpenDartFinaStatDto finaStat = openDartFinaStatApi.getFinaStat(srtnCd);

            stock.setCapital(finaStat.getFirstCapital());
            setBps(stock);
            setPbr(stock);
            setAverageRoe(stock, finaStat);
            setExpectedReturn(stock);
            setPurchasePrice(stock);

            stockRepository.save(stock);
        });
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
            stock.setPbr((double)Math.round((pbr * 100))/ 100);
        }

    }


    private void setAverageRoe(Stock stock, OpenDartFinaStatDto finaStat){
        long firstCapital = finaStat.getFirstCapital();
        long secondCapital = finaStat.getSecondCapital();
        long thirdCapital = finaStat.getThirdCapital();

        long firstProfit = finaStat.getFirstProfit();
        long secondProfit = finaStat.getSecondProfit();
        long thirdProfit = finaStat.getThirdProfit();

        double firstRoe = (double)firstProfit/firstCapital;
        double secondRoe = (double)secondProfit/secondCapital;
        double thirdRoe = (double)thirdProfit/thirdCapital;

        stock.setAverageRoe( (double)Math.round(((firstRoe + secondRoe + thirdRoe) / 3) * 100 )/ 100 + 1);

    }

    private void setExpectedReturn(Stock stock){
        Double expectedReturn = stock.getAverageRoe() / Math.pow(stock.getPbr(), 0.1 );

        if(expectedReturn.isNaN()){
            stock.setExpectedReturn(0.0);
        }
        else{
            stock.setExpectedReturn((double)Math.round((expectedReturn * 100))  / 100 );
        }
    }

    private void setPurchasePrice(Stock stock){
        stock.setPurchasePrice((int)(stock.getBps() * Math.pow(stock.getAverageRoe(), 10) / Math.pow(1.15, 10)));
    }

}

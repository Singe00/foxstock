package kumoh.opensource.foxstock.scheduler;

import kumoh.opensource.foxstock.api.*;
import kumoh.opensource.foxstock.api.dto.CodeDto;
import kumoh.opensource.foxstock.api.dto.NaverDto;
import kumoh.opensource.foxstock.api.dto.PriceDto;
import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import kumoh.opensource.foxstock.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class StockScheduler {

    private final CodeApi codeApi;
    private final PriceApi priceApi;
    private final NaverCrolling naverCrolling;
    private final StockRepository stockRepository;


    @Scheduled(cron = " 0 0 5 * * * ")
    public void dailyUpdate(){
        priceApi.saveAllPrice();

        List<Stock> stocks = stockRepository.findAll();
        stocks.forEach(stock -> {
            PriceDto priceDtoBySrtnCd = priceApi.getPriceDtoBySrtnCd(stock.getSrtnCd());

            stock.setCurrentPrice(Integer.parseInt(priceDtoBySrtnCd.getClpr()));
            stock.setPbr(calculatePbr(stock));
            stock.setExpectedReturn(calculateExpectedReturn(stock));
            stock.setPurchasePrice(calculatePurchasePrice(stock));

            stockRepository.save(stock);
        });

    }

    //@Scheduled
    public void yearlyUpdate(){
        codeApi.saveAllCode();
        priceApi.saveAllPrice();
        naverCrolling.saveAllFinaStat();

        List<CodeDto> codes = codeApi.getAllCode();
        codes.forEach(code -> {
            Stock stock = new Stock();
            stock.setSrtnCd(code.getSrtnCd());
            stock.setName(code.getItmsNm());

            PriceDto priceDtoBySrtnCd = priceApi.getPriceDtoBySrtnCd(code.getSrtnCd());
            stock.setCurrentPrice(Integer.parseInt(priceDtoBySrtnCd.getClpr()));
            stock.setMrktCtg(priceDtoBySrtnCd.getMrktCtg());

            NaverDto finaStatBySrtnCd = naverCrolling.getFinaStatBySrtnCd(code.getSrtnCd());
            stock.setBps(finaStatBySrtnCd.getBps());

            Double pbr = calculatePbr(stock);
            stock.setPbr(pbr);

            Double averageRoe = calculateAverageRoe(finaStatBySrtnCd);
            stock.setAverageRoe(averageRoe);

            Double expectedReturn = calculateExpectedReturn(stock);
            stock.setExpectedReturn(expectedReturn);

            Integer purchasePrice = calculatePurchasePrice(stock);
            stock.setPurchasePrice(purchasePrice);

            stockRepository.save(stock);
        });

        stockRepository.deleteAllByMrktCtg("KONEX");

    }


    private Double calculatePbr(Stock stock) {
        if(stock.getBps() <= 0){
            return 0.0;
        }
        else{
            Double pbr = (double) stock.getCurrentPrice() / stock.getBps();
            return (double)Math.round((pbr * 100)) / 100;
        }
    }


    private Double calculateAverageRoe(NaverDto finaStat){
        double firstRoe = finaStat.getFirstRoe();
        double secondRoe = finaStat.getSecondRoe();
        double thirdRoe = finaStat.getThirdRoe();

        if(firstRoe < 0.0 || secondRoe < 0.0 || thirdRoe <0.0 ||
            firstRoe > 50.0 || secondRoe > 50.0 || thirdRoe > 50.0){
            return 0.0;
        }

        Double averageRoe = (firstRoe + secondRoe + thirdRoe)/ 3;


        return averageRoe / 100 + 1;

    }

    private Double calculateExpectedReturn(Stock stock){
        if(stock.getPbr().equals(0.0)){
            return 0.0;
        }
        else{
            Double expectedReturn = stock.getAverageRoe() / Math.pow(stock.getPbr(), 0.1 );
            return (double)Math.round((expectedReturn * 100))  / 100 ;
        }
    }

    private Integer calculatePurchasePrice(Stock stock){
        if(stock.getAverageRoe() <= 0){
            return 0;
        }
        return (int)(stock.getBps() * Math.pow(stock.getAverageRoe(), 10) / Math.pow(1.15, 10));
    }

}

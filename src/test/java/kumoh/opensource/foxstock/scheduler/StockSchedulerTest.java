package kumoh.opensource.foxstock.scheduler;


import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import kumoh.opensource.foxstock.domain.stock.repository.StockRepository;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class StockSchedulerTest {

    @Autowired
    private StockScheduler stockScheduler;
    @Autowired
    private StockRepository stockRepository;

    @Test
    void schedulerTest(){
        stockScheduler.yearlyUpdate();
    }


    @Test
    void dailyUpdate(){
        stockScheduler.dailyUpdate();
    }

}
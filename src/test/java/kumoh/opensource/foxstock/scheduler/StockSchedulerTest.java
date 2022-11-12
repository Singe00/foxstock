package kumoh.opensource.foxstock.scheduler;


import kumoh.opensource.foxstock.domain.stock.domain.Stock;
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

    @Test
    void schedulerTest(){
        List<Stock> stocks = stockScheduler.yearlyUpdate();
    }

}
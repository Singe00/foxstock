package kumoh.opensource.foxstock.domain.stock.repository;

import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Test
    void repoTest(){
        Stock stock = new Stock("1","1","test",12.1,10_000,12_000,100_000);

        stockRepository.save(stock);

        Stock findStock = stockRepository.findById("1").get();
        System.out.println(findStock);

        Assertions.assertThat(findStock).isEqualTo(stock);
    }

}
package kumoh.opensource.foxstock.domain.stock.repository;

import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Test
    void repoTest(){
        Stock stock = new Stock("111", "222", "test", 10_000, 10_000_000L, 10_000L, 8000, 1.2, 1.16, 1.1, 10000);

        stockRepository.save(stock);
        Stock findStock = stockRepository.findById(stock.getSrtnCd()).get();
        Assertions.assertThat(stock).isEqualTo(findStock);


    }

}
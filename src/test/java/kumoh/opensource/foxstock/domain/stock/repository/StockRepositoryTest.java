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


    }

    @Test
    void deleteTest(){
        stockRepository.deleteAllByMrktCtg("KONEX");
    }

}
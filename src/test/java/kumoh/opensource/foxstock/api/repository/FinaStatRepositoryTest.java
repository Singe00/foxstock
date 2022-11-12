package kumoh.opensource.foxstock.api.repository;

import kumoh.opensource.foxstock.api.dto.FinaStatDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FinaStatRepositoryTest {

    @Autowired
    FinaStatRepository finaStatRepository;

    @Test
    void finaStatTest(){
        List<FinaStatDto> byCrno = finaStatRepository.findAllByCrno("1101110004632");
        System.out.println(byCrno);

        finaStatRepository.deleteAllByEnpTcptAmt("0");
    }

}
package kumoh.opensource.foxstock.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NaverCrollingTest {

    @Autowired
    NaverCrolling naverCrolling;

    @Test
    void crollingTest(){
        String srtnCd = "000020"; //동화약품


    }

}
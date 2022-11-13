package kumoh.opensource.foxstock.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinaStatAPITest {

    @Autowired
    private FinaStatApi finaStatApi;

    @Test
    void finaStatTest(){
        //finaStatApi.saveFinaStatDtoByCrno();
    }

}
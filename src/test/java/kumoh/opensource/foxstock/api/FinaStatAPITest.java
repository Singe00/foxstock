package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FinaStatAPITest {

    @Autowired
    private FinaStatApi finaStatApi;

    @Test
    void finaStatTest(){
        finaStatApi.saveAllFinaStatDto();
    }

}
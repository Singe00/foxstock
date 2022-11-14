package kumoh.opensource.foxstock.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CodeAPITest {

    @Autowired
    private CodeApi codeApi;

    @Test
    void getAllCodeTest(){
        codeApi.saveAllCode();
    }

}
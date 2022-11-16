package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.OpenDartFinaStatDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenDartFinaStatApiTest {

    @Autowired
    private OpenDartFinaStatApi openDartFinaStatApi;

    @Test
    void openDartFinaStatRequestTest(){
        String nexenSrtnCd = "005720";

        openDartFinaStatApi.saveOpenDartFinaStatBySrtnCd(nexenSrtnCd);



    }



}
package kumoh.opensource.foxstock.api;

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
        String url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json";
        String apiKey = ConstServiceKey.OPEN_DART_KEY;
        String nexenCorpCode = "00167192";

        String result = openDartFinaStatApi.request(url, apiKey, nexenCorpCode);

        System.out.println(result);
    }

}
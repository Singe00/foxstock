package kumoh.opensource.foxstock.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class OpenDartCodeApiTest {

    @Autowired
    OpenDartCodeApi openDartCodeApi;

    @Test
    void openDartTest() throws IOException, InterruptedException {
        openDartCodeApi.parsingXml();
    }

}
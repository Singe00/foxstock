package kumoh.opensource.foxstock.api;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CodeAPITest {

    @Test
    void getCodesTest() throws IOException, ParseException {
        CodeAPI codeAPI = new CodeAPI();

        codeAPI.getCodes();


    }

}
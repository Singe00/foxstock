package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class CodeAPITest {

    @Test
    void getCodesTest() throws IOException, ParseException {
        CodeAPI codeAPI = new CodeAPI();

        List<CodeDto> codes = codeAPI.getCodes();

        for (CodeDto cor :
                codes) {
            System.out.println(codes.indexOf(cor) + ":" + cor);
        }

    }

}
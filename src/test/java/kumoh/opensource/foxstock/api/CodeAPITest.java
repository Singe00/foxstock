package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class CodeAPITest {

    @Test
    void getCodesTest() throws IOException, ParseException {
        CodeAPI codeAPI = new CodeAPI();

        Map<String, CodeDto> codes = codeAPI.getCodes();
        ArrayList<CodeDto> codeDtos = new ArrayList<>(codes.values());

        System.out.println(codeDtos.size());
        for (CodeDto cor :
                codeDtos) {
            System.out.println(codeDtos.indexOf(cor) + ":" + cor);
        }

    }

}
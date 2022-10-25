package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class CodeAPITest {

    @Test
    void getCode() throws ParseException, IOException {
        CodeAPI codeAPI = new CodeAPI();
        Map<String, CodeDto> samsungCode = codeAPI.getCodeByItmsNm("삼성전자");
        List<CodeDto> samsungCodeDto = samsungCode.values().stream().toList();

        System.out.println(samsungCodeDto.size());
        for (CodeDto cor :
                samsungCodeDto) {
            System.out.println(samsungCodeDto.indexOf(cor) + ":" + cor);
        }


    }

    @Test
    void getAllCodeTest() throws IOException, ParseException {
        CodeAPI codeAPI = new CodeAPI();

        Map<String, CodeDto> codes = codeAPI.getAllCode();
        ArrayList<CodeDto> codeDtos = new ArrayList<>(codes.values());

        System.out.println(codeDtos.size());
        for (CodeDto cor :
                codeDtos) {
            System.out.println(codeDtos.indexOf(cor) + ":" + cor);
        }

    }

}
package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import kumoh.opensource.foxstock.api.dto.FinaStatDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FinaStatAPITest {

    @Test
    public void getAllFinaStat() throws IOException, ParseException {
        FinaStatAPI finaStatAPI = new FinaStatAPI();
        Map<String, Map<String, FinaStatDto>> totalFinaStatDtos = finaStatAPI.getAllFinaStat();
        Map<String, FinaStatDto> firstYear = totalFinaStatDtos.get("firstYear");
        List<FinaStatDto> firstFinaStatDtos = firstYear.values().stream().toList();

        System.out.println(firstFinaStatDtos.size());
        firstFinaStatDtos.forEach(System.out::println);
    }

    @Test
    public void getFinaStatByCrno() throws IOException, ParseException{
        CodeAPI codeAPI = new CodeAPI();
        Map<String, CodeDto> samsungCode = codeAPI.getCodeByItmsNm("삼성전자");
        CodeDto samsungCodeDto = samsungCode.values().stream().findFirst().get();

        FinaStatAPI finaStatAPI = new FinaStatAPI();
        Map<String, Map<String, FinaStatDto>> totalFinaStatDtos = finaStatAPI.getFinaStatByCrno(samsungCodeDto.getCrno());
        Map<String, FinaStatDto> firstYear = totalFinaStatDtos.get("firstYear");
        List<FinaStatDto> firstFinaStatDtos = firstYear.values().stream().toList();

        System.out.println(firstFinaStatDtos.size());
        firstFinaStatDtos.forEach(System.out::println);
    }


}
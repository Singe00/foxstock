package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import kumoh.opensource.foxstock.api.dto.FinaStatDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FinaStatAPITest {

    @Test
    public void finaStatSimpleTest() throws IOException, ParseException {
        FinaStatAPI finaStatAPI = new FinaStatAPI();
        Map<String, Map<String, FinaStatDto>> totalFinaStatDtos = finaStatAPI.getFinaStat();

        System.out.println(totalFinaStatDtos.values());
    }


}
package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.FnCoFinaStatDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FnCoFinaStatAPITest {

    @Test
    void fnCoFinaStatTest() throws IOException, ParseException {
        FnCoFinaStatAPI fnCoFinaStatAPI = new FnCoFinaStatAPI();
        Map<String, Map<String, FnCoFinaStatDto>> totalFnCOFinaStatDtos = fnCoFinaStatAPI.getFnCoFinaStat();

        System.out.println(totalFnCOFinaStatDtos.size());
        System.out.println(totalFnCOFinaStatDtos.values());

    }

}
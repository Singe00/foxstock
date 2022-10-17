package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import kumoh.opensource.foxstock.api.dto.PriceDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PriceAPITest {

    @Test
    void priceApiTest() throws IOException, ParseException {
        PriceAPI priceAPI = new PriceAPI();
        Map<String, PriceDto> priceDtos = priceAPI.getPrice();

        System.out.println(priceDtos.size());
        for (PriceDto priceDto:
                priceDtos.values()) {
            System.out.println(priceDto);
        }
    }

}
package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.PriceDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

class PriceAPITest {

    @Test
    void getPriceByItmsNm() throws IOException, ParseException{
        PriceAPI priceAPI = new PriceAPI();
        Map<String, PriceDto> priceDtos = priceAPI.getPriceByItmsNm("삼성전자");

        System.out.println(priceDtos.size());
        for (PriceDto priceDto:
                priceDtos.values()) {
            System.out.println(priceDto);
        }
    }

    @Test
    void getAllPrice() throws IOException, ParseException {
        PriceAPI priceAPI = new PriceAPI();
        Map<String, PriceDto> priceDtos = priceAPI.getAllPrice();

        System.out.println(priceDtos.size());
        for (PriceDto priceDto:
                priceDtos.values()) {
            System.out.println(priceDto);
        }
    }

}
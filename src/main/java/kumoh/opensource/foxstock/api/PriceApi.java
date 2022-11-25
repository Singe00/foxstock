package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.PriceDto;
import kumoh.opensource.foxstock.api.format.ApiParsingFormat;
import kumoh.opensource.foxstock.api.format.ApiRequestFormat;
import kumoh.opensource.foxstock.api.repository.PriceDtoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceApi {
    private static final String apiKey = ConstServiceKey.PRICE_SERVICE_KEY;
    private static final String url = "http://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
    private final ApiRequestFormat apiRequestFormat;
    private final ApiParsingFormat apiParsingFormat;

    private final PriceDtoRepository priceDtoRepository;

    public PriceDto getPriceDtoBySrtnCd(String srtnCd) {
        return priceDtoRepository.findById(srtnCd).get();
    }

    public void saveAllPrice(){
        String result = apiRequestFormat.request(url,apiKey,null,null);

        List<PriceDto> priceDtos = apiParsingFormat.priceParsing(result);

        priceDtoRepository.saveAll(priceDtos);
    }


}

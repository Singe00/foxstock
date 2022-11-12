package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import kumoh.opensource.foxstock.api.format.ApiParsingFormat;
import kumoh.opensource.foxstock.api.format.ApiRequestFormat;
import kumoh.opensource.foxstock.api.repository.CodeDtoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class CodeApi {

    private static final String apiKey = ConstServiceKey.CODE_SERVICE_KEY;
    private static final String url = "http://apis.data.go.kr/1160100/service/GetKrxListedInfoService/getItemInfo";
    private final ApiRequestFormat apiRequestFormat;
    private final ApiParsingFormat apiParsingFormat;
    private final CodeDtoRepository codeDtoRepository;

    public List<CodeDto> getAllCode() {
        return codeDtoRepository.findAll();
    }

    public void saveAllCode(){
        String result = apiRequestFormat.request(url, apiKey, null);
        List<CodeDto> parsedResult = apiParsingFormat.codeParsing(result);
        codeDtoRepository.saveAll(parsedResult);
    }
}

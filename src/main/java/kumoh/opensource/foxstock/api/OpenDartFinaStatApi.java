package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.OpenDartFinaStatDto;
import kumoh.opensource.foxstock.api.repository.OpenDartCodeDtoRepository;
import kumoh.opensource.foxstock.api.repository.OpenDartFinaStatDtoRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class OpenDartFinaStatApi {

    private final CodeApi codeApi;
    private final OpenDartCodeDtoRepository openDartCodeDtoRepository;
    private final OpenDartFinaStatDtoRepository openDartFinaStatDtoRepository;
    private static final String url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json";
    private static final String apiKey = ConstServiceKey.OPEN_DART_KEY;

    public void deleteAllFinaStat(){
        openDartFinaStatDtoRepository.deleteAll();
    }

    public OpenDartFinaStatDto getFinaStat(String srtnCd){
         return openDartFinaStatDtoRepository.findById(srtnCd).get();
    }

    public void saveOpenDartFinaStatBySrtnCd(String srtnCd){
        String corpCode = openDartCodeDtoRepository.findById(srtnCd).get().getCorpCode();
        String response = request(url, apiKey,corpCode,"CFS");
        if(!isConsolidate(response)){
            response = request(url,apiKey,corpCode,"OFS");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        OpenDartFinaStatDto parsed = parsing(srtnCd, response);

        openDartFinaStatDtoRepository.save(parsed);

    }

    private OpenDartFinaStatDto parsing(String srtnCd, String response){
        JSONParser jsonParser = new JSONParser();
        JSONObject parsed = null;
        try {
            parsed = (JSONObject) jsonParser.parse(response);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONArray list = (JSONArray) parsed.get("list");

        String corpCode = openDartCodeDtoRepository.findById(srtnCd).get().getCorpCode();
        String corpName = openDartCodeDtoRepository.findById(srtnCd).get().getCorpName();

        OpenDartFinaStatDto openDartFinaStatDto = new OpenDartFinaStatDto(srtnCd,corpCode,corpName,
                -1L,-1L,-1L,-1L,-1L,-1L);

        if(list ==null){
            return openDartFinaStatDto;
        }


        for(int i = 0; i < list.size(); i++){
            JSONObject obj = (JSONObject) list.get(i);

            if(obj.get("account_id").equals("ifrs-full_ProfitLossAttributableToOwnersOfParent")){
                String firstProfitString = (String) obj.get("thstrm_amount");
                String secondProfitString = (String) obj.get("frmtrm_amount");
                String thirdProfitString = (String) obj.get("bfefrmtrm_amount");

                if(!isStringNullOrBlank(firstProfitString)){
                    openDartFinaStatDto.setFirstProfit(Long.parseLong(firstProfitString));
                }

                if(!isStringNullOrBlank(secondProfitString)){
                    openDartFinaStatDto.setSecondProfit(Long.parseLong(secondProfitString));
                }

                if(!isStringNullOrBlank(thirdProfitString)){
                    openDartFinaStatDto.setThirdProfit(Long.parseLong(thirdProfitString));
                }

            }

            if(obj.get("account_id").equals("ifrs-full_EquityAttributableToOwnersOfParent")){
                String firstCapitalString = (String) obj.get("thstrm_amount");
                String secondCapitalString = (String) obj.get("frmtrm_amount");
                String thirdCapitalString = (String) obj.get("bfefrmtrm_amount");

                if(!isStringNullOrBlank(firstCapitalString)){
                    openDartFinaStatDto.setFirstCapital(Long.parseLong(firstCapitalString));
                }

                if(!isStringNullOrBlank(secondCapitalString)){
                    openDartFinaStatDto.setSecondCapital(Long.parseLong(secondCapitalString));
                }

                if(!isStringNullOrBlank(thirdCapitalString)){
                    openDartFinaStatDto.setThirdCapital(Long.parseLong(thirdCapitalString));
                }
            }

        }

        return openDartFinaStatDto;
    }

    private boolean isStringNullOrBlank(String string){
        return string == null || string.isBlank();
    }

    private boolean isConsolidate(String response){
        JSONParser jsonParser = new JSONParser();
        JSONObject parsed = null;
        try {
            parsed = (JSONObject) jsonParser.parse(response);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(parsed.get("status").equals("013")){
            return false;
        }
        return true;
    }

    private String request(String endUrl, String apiKey, String corpCode, String docType ) {
        String firstYear = Integer.toString(LocalDate.now().getYear()-1);
        String totalDocCode = "11011";

        RestTemplate restTemplate = new RestTemplate();

        UriComponents url = UriComponentsBuilder
                .fromHttpUrl(endUrl)
                .queryParam("crtfc_key", apiKey)
                .queryParam("corp_code", corpCode)
                .queryParam("bsns_year", firstYear)
                .queryParam("reprt_code", totalDocCode)
                .queryParam("fs_div", docType)
                .build();


        return restTemplate.exchange(url.toUri(), HttpMethod.GET,null,String.class).getBody();


    }

}

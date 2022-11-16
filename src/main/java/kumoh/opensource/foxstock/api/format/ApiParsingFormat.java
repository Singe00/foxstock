package kumoh.opensource.foxstock.api.format;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import kumoh.opensource.foxstock.api.dto.PriceDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiParsingFormat {


    public List<CodeDto> codeParsing(String result){
        JSONArray jsonArray = getJsonArray(result);

        List<CodeDto> codes = new ArrayList<>();
        //crno

        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj = (JSONObject) jsonArray.get(i);
            CodeDto codeDto = new CodeDto();
            String srtnCd = (String)obj.get("srtnCd");
            String fixSrtnCd = srtnCd.substring(1);

            codeDto.setSrtnCd(fixSrtnCd);
            codeDto.setItmsNm((String)obj.get("itmsNm"));
            codeDto.setCrno((String)obj.get("crno"));
            if(codes.contains(codeDto)){
                break;
            }
            codes.add(codeDto);
        }
        return codes;
    }


    public List<PriceDto> priceParsing(String result) {

        JSONArray jsonArray = getJsonArray(result);
        List<PriceDto> priceDtos = new ArrayList<>();
        //srtnCd

        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj = (JSONObject) jsonArray.get(i);
            PriceDto priceDto = new PriceDto();
            priceDto.setSrtnCd((String) obj.get("srtnCd"));
            priceDto.setClpr((String) obj.get("clpr"));
            priceDto.setLstgStCnt((String) obj.get("lstgStCnt"));
            priceDto.setMrktCtg((String) obj.get("mrktCtg"));

            priceDtos.add(priceDto);
        }
        return priceDtos;
    }

    private JSONArray getJsonArray(String result){
        JSONParser jsonParser = new JSONParser();
        JSONObject parsed = null;
        try {
            parsed = (JSONObject) jsonParser.parse(result);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ;
        JSONObject response = (JSONObject) parsed.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");

        return (JSONArray) items.get("item");

    }

}

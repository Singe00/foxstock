package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class CodeAPI {

    private static final String apiKey = ConstServiceKey.CODE_SERVICE_KEY;
    private static final String url = "http://apis.data.go.kr/1160100/service/GetKrxListedInfoService/getItemInfo";

    public Map<String,CodeDto> getCodeByItmsNm(String itmsNm) throws ParseException, IOException {
        String result = request("1", itmsNm);

        Map<String, CodeDto> code = parseResult(result);

        return code;
    }

    public Map<String, CodeDto> getAllCode() throws IOException, ParseException {
        String result = request("10000", null);

        Map<String, CodeDto> codes = parseResult(result);

        return codes;
    }

    private static Map<String, CodeDto> parseResult(String result) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject parsed = (JSONObject) jsonParser.parse(result);
        JSONObject response = (JSONObject) parsed.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        Map<String, CodeDto> codes = new HashMap<>();
        //crno

        for(int i = 0; i < item.size(); i++){
            JSONObject obj = (JSONObject) item.get(i);
            CodeDto codeDto = new CodeDto();
            String srtnCd = (String)obj.get("srtnCd");
            String fixSrtnCd = srtnCd.substring(1);

            codeDto.setSrtnCd(fixSrtnCd);
            codeDto.setItmsNm((String)obj.get("itmsNm"));
            codeDto.setCrno((String)obj.get("crno"));
            if(codes.containsKey(codeDto.getCrno())){
                break;
            }
            codes.put(codeDto.getCrno(), codeDto);
        }
        return codes;
    }

    private static String request(String numOfRows, @Nullable String itmsNm) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + apiKey );
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1",StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("resultType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json",StandardCharsets.UTF_8));

        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(numOfRows,StandardCharsets.UTF_8));
        if(itmsNm != null){
            urlBuilder.append("&" + URLEncoder.encode("itmsNm", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(itmsNm,StandardCharsets.UTF_8));
        }

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-type","application/json");

        BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

        String result = rd.readLine();
        rd.close();
        con.disconnect();
        return result;
    }
}

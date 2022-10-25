package kumoh.opensource.foxstock.api;


import kumoh.opensource.foxstock.api.dto.FinaStatDto;
import lombok.RequiredArgsConstructor;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinaStatAPI {

    private static final String apiKey = ConstServiceKey.FINA_STAT_SERVICE_KEY;
    private static final String url = "http://apis.data.go.kr/1160100/service/GetFinaStatInfoService/getSummFinaStat";

    public Map<String,Map<String,FinaStatDto>> getFinaStatByCrno(String crno) throws IOException, ParseException {
        int nowYear = LocalDate.now().getYear();
        String firstYear = Integer.toString(nowYear-1);
        String secondYear = Integer.toString(nowYear-2);
        String thirdYear = Integer.toString(nowYear-3);

        String firstRequest = request(firstYear, crno);
        String secondRequest = request(secondYear, crno);
        String thirdRequest = request(thirdYear, crno);

        Map<String, FinaStatDto> firstFinaStatDtos = parsingRequest(firstRequest);
        Map<String, FinaStatDto> secondFinaStatDtos = parsingRequest(secondRequest);
        Map<String, FinaStatDto> thridFinaStatDtos = parsingRequest(thirdRequest);

        Map<String, Map<String, FinaStatDto>> totalFinaStatDtos = new HashMap<>();
        totalFinaStatDtos.put("firstYear", firstFinaStatDtos);
        totalFinaStatDtos.put("secondYear", secondFinaStatDtos);
        totalFinaStatDtos.put("thirdYear", thridFinaStatDtos);

        return totalFinaStatDtos;
    }

    public Map<String,Map<String, FinaStatDto>> getAllFinaStat() throws IOException, ParseException {

        int nowYear = LocalDate.now().getYear();
        String firstYear = Integer.toString(nowYear-1);
        String secondYear = Integer.toString(nowYear-2);
        String thirdYear = Integer.toString(nowYear-3);

        String firstRequest = request(firstYear, null);
        String secondRequest = request(secondYear, null);
        String thirdRequest = request(thirdYear, null);

        Map<String, FinaStatDto> firstFinaStatDtos = parsingRequest(firstRequest);
        Map<String, FinaStatDto> secondFinaStatDtos = parsingRequest(secondRequest);
        Map<String, FinaStatDto> thridFinaStatDtos = parsingRequest(thirdRequest);

        Map<String, Map<String, FinaStatDto>> totalFinaStatDtos = new HashMap<>();
        totalFinaStatDtos.put("firstYear", firstFinaStatDtos);
        totalFinaStatDtos.put("secondYear", secondFinaStatDtos);
        totalFinaStatDtos.put("thirdYear", thridFinaStatDtos);

        return totalFinaStatDtos;
    }


    private String request(String bizYear, @Nullable String crno) throws IOException {

        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + apiKey );
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("10000",StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1",StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("resultType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json",StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("bizYear", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(bizYear,StandardCharsets.UTF_8));
        if(crno != null){
            urlBuilder.append("&" + URLEncoder.encode("crno", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(crno,StandardCharsets.UTF_8));
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

    private Map<String, FinaStatDto> parsingRequest(String requestResult) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject parsed = (JSONObject) jsonParser.parse(requestResult);
        JSONObject response = (JSONObject) parsed.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        Map<String,FinaStatDto> finaStatDtoList = new HashMap<>();

        for(int i = 0; i < item.size(); i++){
            JSONObject obj = (JSONObject) item.get(i);

            FinaStatDto finaStatDto = new FinaStatDto();
            if(obj.get("fnclDcdNm").equals("연결요약재무제표") || obj.get("fnclDcdNm").equals("요약연결재무제표")) {
                String crno = (String) obj.get("crno");
                finaStatDto.setCrno((String)obj.get("crno"));
                finaStatDto.setBizYear((String) obj.get("bizYear"));
                finaStatDto.setEnpCrtmNpf((String) obj.get("enpCrtmNpf"));
                finaStatDto.setEnpTcptAmt((String) obj.get("enpTcptAmt"));
                finaStatDtoList.put(finaStatDto.getCrno(), finaStatDto);
            }
        }

        return finaStatDtoList;
    }

}

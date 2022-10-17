package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.FinaStatDto;
import kumoh.opensource.foxstock.api.dto.FnCoFinaStatDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

public class FnCoFinaStatAPI {
    private static final String apiKey = ConstServiceKey.FNCO_FINA_STAT_SERVICE_KEY;
    private static final String url = "http://apis.data.go.kr/1160100/service/GetFnCoFinaStatCredInfoService/getFnCoSummFinaStat";

    public Map<String, Map<String, FnCoFinaStatDto>> getFnCoFinaStat() throws IOException, ParseException {

        int nowYear = LocalDate.now().getYear();
        String firstYear = Integer.toString(nowYear-1);
        String secondYear = Integer.toString(nowYear-2);
        String thirdYear = Integer.toString(nowYear-3);

        String firstRequest = request(firstYear);
        String secondRequest = request(secondYear);
        String thirdRequest = request(thirdYear);

        Map<String, FnCoFinaStatDto> firstFinaStatDtos = parsingRequest(firstRequest);
        Map<String, FnCoFinaStatDto> secondFinaStatDtos = parsingRequest(secondRequest);
        Map<String, FnCoFinaStatDto> thridFinaStatDtos = parsingRequest(thirdRequest);

        Map<String, Map<String, FnCoFinaStatDto>> totalFnCoFinaStatDtos = new HashMap<>();
        totalFnCoFinaStatDtos.put("firstYear", firstFinaStatDtos);
        totalFnCoFinaStatDtos.put("secondYear", secondFinaStatDtos);
        totalFnCoFinaStatDtos.put("thirdYear", thridFinaStatDtos);

        return totalFnCoFinaStatDtos;
    }


    private String request(String bizYear) throws IOException {

        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + apiKey );
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("10000",StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1",StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("resultType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json",StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("bizYear", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(bizYear,StandardCharsets.UTF_8));

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

    private Map<String, FnCoFinaStatDto> parsingRequest(String requestResult) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject parsed = (JSONObject) jsonParser.parse(requestResult);
        JSONObject response = (JSONObject) parsed.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        Map<String,FnCoFinaStatDto> fnCoFinaStatDtoList = new HashMap<>();

        for(int i = 0; i < item.size(); i++){
            JSONObject obj = (JSONObject) item.get(i);
            FnCoFinaStatDto fnCoFinaStatDto = new FnCoFinaStatDto();
            if(obj.get("fnclDcdNm").equals("연결요약재무제표")) {
                fnCoFinaStatDto.setCrno((String)obj.get("crno"));
                fnCoFinaStatDto.setBizYear((String) obj.get("bizYear"));
                fnCoFinaStatDto.setFncoCrtmNpf((String) obj.get("fncoCrtmNpf"));
                fnCoFinaStatDto.setFncoTcptAmt((String) obj.get("fncoTcptAmt"));
            }
            fnCoFinaStatDtoList.put(fnCoFinaStatDto.getCrno(), fnCoFinaStatDto);

        }

        return fnCoFinaStatDtoList;
    }

}

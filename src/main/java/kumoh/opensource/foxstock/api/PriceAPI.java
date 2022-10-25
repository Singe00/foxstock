package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.PriceDto;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PriceAPI {
    private static final String apiKey = ConstServiceKey.PRICE_SERVICE_KEY;
    private static final String url = "http://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";

    public Map<String, PriceDto> getPriceByItmsNm(String itmsNm) throws IOException, ParseException {
        String result = request("1",itmsNm);

        Map<String, PriceDto> priceDtos = parsing(result);

        return priceDtos;
    }

    public Map<String, PriceDto> getAllPrice() throws IOException, ParseException {
        String result = request("10000",null);

        Map<String, PriceDto> priceDtos = parsing(result);

        return priceDtos;
    }

    private static Map<String, PriceDto> parsing(String result) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject parsed = (JSONObject) jsonParser.parse(result);
        JSONObject response = (JSONObject) parsed.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        Map<String, PriceDto> priceDtos = new HashMap<>();
        //srtnCd

        for(int i = 0; i < item.size(); i++){
            JSONObject obj = (JSONObject) item.get(i);
            PriceDto priceDto = new PriceDto();
            priceDto.setSrtnCd((String) obj.get("srtnCd"));
            priceDto.setClpr((String) obj.get("clpr"));
            priceDto.setLstgStCnt((String) obj.get("lstgStCnt"));

            priceDtos.put(priceDto.getSrtnCd(), priceDto);
        }
        return priceDtos;
    }

    private static String request(String numOfRows, @Nullable String itmsNm) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + apiKey );
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(numOfRows,StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1",StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("resultType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json",StandardCharsets.UTF_8));

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

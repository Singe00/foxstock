package kumoh.opensource.foxstock.api.format;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class ApiRequestFormat {

    public String request(String endUrl, String apiKey, @Nullable String bizYear, @Nullable String crno){
        StringBuilder urlBuilder = new StringBuilder(endUrl);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + apiKey );
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("10000",StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1",StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("resultType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json",StandardCharsets.UTF_8));

        HttpURLConnection con = null;

        try{
            URL url = new URL(urlBuilder.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(60000);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type","application/json");

            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

            String result = rd.readLine();

            rd.close();

            return result;
        }
        catch (IOException e){
            if(con != null){
                con.disconnect();
            }

            throw new RuntimeException(e);
        }
    }
}

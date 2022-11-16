package kumoh.opensource.foxstock.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class OpenDartFinaStatApi {

    private final CodeApi codeApi;
    private static final String url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json";
    private static final String apiKey = ConstServiceKey.OPEN_DART_KEY;

    public void saveAllOpenDartFinaStat(){


    }

    public String request(String endUrl, String apiKey, String corpCode ) {
        String firstYear = Integer.toString(LocalDate.now().getYear()-1);
        String totalDocCode = "11011";
        String consolidate = "CFS";

        RestTemplate restTemplate = new RestTemplate();
        UriComponents url = UriComponentsBuilder
                .fromHttpUrl(endUrl)
                .queryParam("crtfc_key", apiKey)
                .queryParam("corp_code", corpCode)
                .queryParam("bsns_year", firstYear)
                .queryParam("reprt_code", totalDocCode)
                .queryParam("fs_div", consolidate)
                .build();

        return restTemplate.exchange(url.toUri(), HttpMethod.GET,null,String.class).getBody();


    }

}

package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import kumoh.opensource.foxstock.api.dto.NaverDto;
import kumoh.opensource.foxstock.api.repository.CodeDtoRepository;
import kumoh.opensource.foxstock.api.repository.NaverDtoRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NaverCrolling {

    private final NaverDtoRepository naverDtoRepository;
    private final CodeApi codeApi;

    public void saveAllFinaStat(){
        List<String> srtnCds = codeApi.getAllCode().stream().map(CodeDto::getSrtnCd).toList();
        srtnCds.forEach(this::saveFinaStatBySrtnCd);

    }

    public NaverDto getFinaStatBySrtnCd(String srtnCd){
        return naverDtoRepository.findById(srtnCd).orElse(new NaverDto("-1", -1.0, -1.0, -1.0, -1));
    }

    private void saveFinaStatBySrtnCd(String srtnCd){
        String url = "https://finance.naver.com/item/main.naver?code=" + srtnCd;
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String firstRoeStr = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(6) > td:nth-child(4)").text().replace(",","");
        String secondRoeStr = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(6) > td:nth-child(3)").text().replace(",","");
        String thirdRoeStr = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(6) > td:nth-child(2)").text().replace(",","");
        String bpsStr = document.select("#content > div.section.cop_analysis > div.sub_section > table > tbody > tr:nth-child(12) > td:nth-child(4)").text().replace(",","");

        if(firstRoeStr.isBlank() || firstRoeStr.equals("-")){
            firstRoeStr = "0";
        }

        if(secondRoeStr.isBlank() || secondRoeStr.equals("-")){
            secondRoeStr = "0";
        }

        if(thirdRoeStr.isBlank() || thirdRoeStr.equals("-")){
            thirdRoeStr = "0";
        }

        if(bpsStr.isBlank() || bpsStr.equals("-")){
            bpsStr = "0";
        }

        Double firstRoe = Double.parseDouble(firstRoeStr);
        Double secondRoe = Double.parseDouble(secondRoeStr);
        Double thirdRoe = Double.parseDouble(thirdRoeStr);
        Integer bps = Integer.parseInt(bpsStr);

        NaverDto naverDto = new NaverDto(srtnCd,firstRoe,secondRoe,thirdRoe,bps);

        naverDtoRepository.save(naverDto);

    }
}

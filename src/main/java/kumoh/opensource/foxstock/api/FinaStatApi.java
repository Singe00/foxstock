package kumoh.opensource.foxstock.api;


import kumoh.opensource.foxstock.api.dto.FinaStatDto;
import kumoh.opensource.foxstock.api.format.ApiParsingFormat;
import kumoh.opensource.foxstock.api.format.ApiRequestFormat;
import kumoh.opensource.foxstock.api.repository.FinaStatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinaStatApi {

    private static final String apiKey = ConstServiceKey.FINA_STAT_SERVICE_KEY;
    private static final String url = "http://apis.data.go.kr/1160100/service/GetFinaStatInfoService/getSummFinaStat";
    private final ApiRequestFormat apiRequestFormat;
    private final ApiParsingFormat apiParsingFormat;
    private final FinaStatRepository finaStatRepository;


    public List<FinaStatDto> getFinaStatByCrno(String crno){

        return finaStatRepository.findAllByCrno(crno);
    }


    public void saveAllFinaStatDto(){
        int nowYear = LocalDate.now().getYear();
        String firstYear = Integer.toString(nowYear-1);
        String secondYear = Integer.toString(nowYear-2);
        String thirdYear = Integer.toString(nowYear-3);

        saveFinaStatByBizYear(firstYear);
        saveFinaStatByBizYear(secondYear);
        saveFinaStatByBizYear(thirdYear);
    }

    private void saveFinaStatByBizYear(String bizYear){
        finaStatRepository.deleteAll();
        String request = apiRequestFormat.request(url,apiKey, bizYear);

        List<FinaStatDto> finaStatDtos = apiParsingFormat.finaStatParsing(request);

        finaStatRepository.saveAll(finaStatDtos);
        finaStatRepository.deleteAllByEnpTcptAmt("0");
    }

}

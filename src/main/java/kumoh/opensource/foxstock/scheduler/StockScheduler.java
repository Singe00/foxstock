package kumoh.opensource.foxstock.scheduler;

import kumoh.opensource.foxstock.api.CodeAPI;
import kumoh.opensource.foxstock.api.FinaStatAPI;
import kumoh.opensource.foxstock.api.FnCoFinaStatAPI;
import kumoh.opensource.foxstock.api.PriceAPI;
import kumoh.opensource.foxstock.api.dto.CodeDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StockScheduler {

    CodeAPI codeAPI;
    FinaStatAPI finaStatAPI;
    FnCoFinaStatAPI fnCoFinaStatAPI;
    PriceAPI priceAPI;

    @Scheduled
    private void dailyUpdate() throws IOException, ParseException {
        Map<String, CodeDto> codes = codeAPI.getCodes();


    }

    @Scheduled
    private void yearlyUpdate(){


    }

}

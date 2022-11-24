package kumoh.opensource.foxstock.Search;

import kumoh.opensource.foxstock.domain.member.Dto.FindUserIdDto;
import kumoh.opensource.foxstock.domain.member.Service.InterestService;
import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {

    @Autowired
    private final SearchService searchService;

    @GetMapping("/stock/search")
    public List<Stock> getStockSearch(@RequestBody SearchDto request){
        List<Stock> searchList = searchService.search(request);
        return searchList;
    }

}

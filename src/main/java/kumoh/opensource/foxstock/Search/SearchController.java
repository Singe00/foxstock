package kumoh.opensource.foxstock.Search;

import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {

    @Autowired
    private final SearchRepository searchRepository;

    @GetMapping("/stock/search")
    public Object StockSearch(@RequestBody SearchDto request,@RequestParam int page){
        PageRequest pageRequest = PageRequest.of(page,15, Sort.by(Sort.Direction.DESC, "expectedReturn"));
        return searchRepository.findAllByNameContaining(request.getSearchString(),pageRequest);
    }
}

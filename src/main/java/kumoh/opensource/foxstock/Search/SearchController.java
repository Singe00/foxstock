package kumoh.opensource.foxstock.Search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {

    @Autowired
    private final SearchRepository searchRepository;

    @GetMapping("/stock/search")
    public Object StockSearch(@RequestParam String query){
        PageRequest pageRequest = PageRequest.of(0,Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "expectedReturn"));
        return searchRepository.findAllByNameContaining(query,pageRequest);
    }
}

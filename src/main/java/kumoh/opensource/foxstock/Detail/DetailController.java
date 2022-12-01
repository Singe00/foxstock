package kumoh.opensource.foxstock.Detail;

import kumoh.opensource.foxstock.Search.SearchRepository;
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
public class DetailController {

    @Autowired
    private final DetailService detailService;

    @GetMapping("/stock/detail")
    @ResponseBody
    public Detail StockSearch(@RequestParam String name,@RequestParam String srtn_cd){
        return detailService.returnDetail(name,srtn_cd);
    }
}
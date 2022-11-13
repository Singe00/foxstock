package kumoh.opensource.foxstock.domain.stock.controller;

import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import kumoh.opensource.foxstock.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockRepository stockRepository;

    @GetMapping("/allStock")
    public Object getAllStock(){
        return stockRepository.findAll();
    }

    @GetMapping("/stock/all")
    public Object getAllStockOrderedPage(@RequestParam int page){
        PageRequest pageRequest = PageRequest.of(page,30,Sort.by(Sort.Direction.DESC, "expectedReturn"));
        return stockRepository.findAll(pageRequest);

    }

    @GetMapping("/stock/kospi")
    public Object getKospiOrderedPage(@RequestParam int page){
        PageRequest pageRequest = PageRequest.of(page,30,Sort.by(Sort.Direction.DESC, "expectedReturn"));
        return stockRepository.findAllByMrktCtg("KOSPI", pageRequest);

    }

    @GetMapping("/stock/kosdaq")
    public Object getKosdaqOrderedPage(@RequestParam int page){
        PageRequest pageRequest = PageRequest.of(page,30,Sort.by(Sort.Direction.DESC, "expectedReturn"));
        return stockRepository.findAllByMrktCtg("KOSDAQ", pageRequest);

    }



}

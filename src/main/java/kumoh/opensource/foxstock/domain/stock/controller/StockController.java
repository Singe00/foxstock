package kumoh.opensource.foxstock.domain.stock.controller;

import kumoh.opensource.foxstock.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockRepository stockRepository;

    @GetMapping("/allStock")
    public Object getAllStock(){
        return stockRepository.findAll();
    }

}

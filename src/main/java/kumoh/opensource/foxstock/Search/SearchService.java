package kumoh.opensource.foxstock.Search;

import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    @Autowired
    private final SearchRepository searchRepository;

    public List<Stock> search(SearchDto request) {
        List<Stock> searchList = Collections.emptyList();
        searchList = searchRepository.findAllBySrtnCd(request.getSearchString());
        if (searchList.isEmpty())
            searchList = searchRepository.findAllByNameContaining(request.getSearchString());
        //

        for (int i = 0;i<searchList.size();i++)
            log.info("{},{}",searchList.get(i).getSrtnCd(),searchList.get(i).getName());
        return searchList;
    }
}

package kumoh.opensource.foxstock.domain.member.Service;

import kumoh.opensource.foxstock.Search.SearchDto;
import kumoh.opensource.foxstock.Search.SearchService;
import kumoh.opensource.foxstock.domain.member.Dto.InterestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InterestServiceTest {

    @Autowired
    private InterestService interestService;
    @Autowired
    private SearchService searchService;

    @Test
    void addInterest() {
        InterestDto interestDto = new InterestDto();
        interestDto.setEmail("idid");
        interestDto.setSrtnCd("000040");

        interestService.addInterest(interestDto);
    }

    @Test
    void deleteInterest() {
        InterestDto interestDto = new InterestDto();
        interestDto.setEmail("idid");
        interestDto.setSrtnCd("000040");

        interestService.deleteInterest(interestDto);
    }

    @Test
    void returnInterest() {
        InterestDto interestDto = new InterestDto();
        interestDto.setEmail("idid");

        interestService.returnInterest2(interestDto);
    }

    @Test
    void searchName() {
        SearchDto searchDto = new SearchDto();
        searchDto.setSearchString("전자");

        searchService.search(searchDto);
    }


}
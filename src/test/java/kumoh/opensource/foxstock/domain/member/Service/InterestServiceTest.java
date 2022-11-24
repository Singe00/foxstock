package kumoh.opensource.foxstock.domain.member.Service;

import kumoh.opensource.foxstock.Search.SearchDto;
import kumoh.opensource.foxstock.domain.member.Dto.InterestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InterestServiceTest {

    @Autowired
    private InterestService interestService;

    @Test
    void addInterest() {
        InterestDto interestDto = new InterestDto();
        interestDto.setEmail("idid");
        interestDto.setSrtnCd("000020");

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


}
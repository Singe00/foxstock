package kumoh.opensource.foxstock.api.repository;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeDtoRepositoryTest {

    @Autowired
    private CodeDtoRepository codeDtoRepository;

    @Test
    @Transactional
    void codeRepoTest(){
        CodeDto codeDto = new CodeDto("srtnCd", "itmsNm", "crno");
        codeDtoRepository.save(codeDto);
        CodeDto findCodeDto = codeDtoRepository.findById("srtnCd").get();

        Assertions.assertThat(findCodeDto).isEqualTo(codeDto);

    }

}
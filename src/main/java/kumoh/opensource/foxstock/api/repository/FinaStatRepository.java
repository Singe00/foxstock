package kumoh.opensource.foxstock.api.repository;

import kumoh.opensource.foxstock.api.dto.FinaStatDto;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface FinaStatRepository extends JpaRepository<FinaStatDto, Integer> {
    List<FinaStatDto> findAllByCrno(String crno);
    @Transactional
    void deleteAllByEnpTcptAmt(String enpTcptAmt);

}

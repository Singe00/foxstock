package kumoh.opensource.foxstock.domain.member.Repository;

import kumoh.opensource.foxstock.domain.member.Domain.Interest;
import kumoh.opensource.foxstock.domain.member.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<Interest,Long> {
    Optional<Interest> findByUserIdAndSrtnCd(Long userId,String srtnCd);

    List<Interest> findAllByUserId(Long userId);
}

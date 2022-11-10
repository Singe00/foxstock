package kumoh.opensource.foxstock.domain.member.Repository;

import kumoh.opensource.foxstock.domain.member.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByUserName(String userName);
}

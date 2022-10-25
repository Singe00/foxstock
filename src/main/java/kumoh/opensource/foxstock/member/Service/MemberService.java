package kumoh.opensource.foxstock.member.Service;

import kumoh.opensource.foxstock.member.Domain.Member;
import kumoh.opensource.foxstock.member.Dto.MemberDto;
import kumoh.opensource.foxstock.member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;

    public String signup(MemberDto request){
        memberRepository.save(Member.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .build());
        return "Success";
    }

    public String login(String userId,String password) {
        Optional<Member> member = memberRepository.findByUserId(userId);
        log.info("db password = {}, input password = {}",member.get().getPassword(),password);
        if (member.get().getPassword().equals(password)) {
            return "Success";
        }
        return "Failed";
    }

    public Optional<Member> findOne(String  id) {
        return memberRepository.findByUserId(id);
    }

}

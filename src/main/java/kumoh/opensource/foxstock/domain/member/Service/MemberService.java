package kumoh.opensource.foxstock.domain.member.Service;

import kumoh.opensource.foxstock.domain.member.Domain.Member;
import kumoh.opensource.foxstock.domain.member.Dto.MemberDto;
import kumoh.opensource.foxstock.domain.member.Repository.MemberRepository;
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

    Member member = new Member();
    public String signup(MemberDto request){
        memberRepository.findByUserId(request.getUserId())
                        .ifPresent(m->{
                            throw new IllegalStateException("이미 존재하는 아이디입니다.");
                        });

        member.setId(null);
        member.setUserId(request.getUserId());
        member.setPassword(request.getPassword());

        memberRepository.save(member);

        return "Success";
    }

    public String login(MemberDto request) {
        if (memberRepository.findByUserId(request.getUserId()).isPresent())
        {
            Optional<Member> member = memberRepository.findByUserId(request.getUserId());
            log.info("db password = {}, input password = {}",member.get().getPassword(),request.getPassword());
            if (member.get().getPassword().equals(request.getPassword())) {
                return "Success";
            }
            else{
                throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
            }
        }
        else {
            throw new IllegalStateException("존재하지 않는 아이디입니다.");
        }
    }
}

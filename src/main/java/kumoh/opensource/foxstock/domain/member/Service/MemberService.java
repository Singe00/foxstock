package kumoh.opensource.foxstock.domain.member.Service;

import kumoh.opensource.foxstock.domain.member.Domain.Interest;
import kumoh.opensource.foxstock.domain.member.Domain.Member;
import kumoh.opensource.foxstock.domain.member.Dto.*;
import kumoh.opensource.foxstock.domain.member.Repository.InterestRepository;
import kumoh.opensource.foxstock.domain.member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
        memberRepository.findByEmail(request.getEmail())
                        .ifPresent(m->{
                            throw new IllegalStateException("이미 존재하는 아이디입니다.");
                        });
        if (request.getPassword().equals(request.getCheckPassword()))
        {
            member.setId(null);
            member.setName(request.getName());
            member.setEmail(request.getEmail());
            member.setPassword(request.getPassword());
            member.setUserCheckQuestionNumber(request.getUserCheckQuestionNumber());
            member.setUserCheckQuestionAnswer(request.getUserCheckQuestionAnswer());

            memberRepository.save(member);

            return "Success";
        }
        else {
            return "비밀번호를 확인해주세요.";
        }
    }

    public String login(MemberDto request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent())
        {
            Optional<Member> member = memberRepository.findByEmail(request.getEmail());
            log.info("db password = {}, input password = {}",member.get().getPassword(),request.getPassword());
            if (member.get().getPassword().equals(request.getPassword())) {
                return "Success";
            }
            else{
                return "비밀번호가 일치하지 않습니다.";
            }
        }
        else {
            return "존재하지 않는 아이디입니다.";
        }
    }

    public String findUserId(FindUserIdDto request) {
        if (memberRepository.findByName(request.getName()).isPresent()) {
            Optional<Member> member = memberRepository.findByName(request.getName());
            if (member.get().getUserCheckQuestionNumber() == request.getUserCheckQuestionNumber()) {
                if (member.get().getUserCheckQuestionAnswer().equals(request.getUserCheckQuestionAnswer())) {
                    log.info("GOOOODDDDD");
                    return member.get().getEmail();
                }
                else {
                    log.info("FAILLLLLLL");
                    return "Fail";
                }
            } else {
                log.info("FAILLLLLLL");
                return "Fail";
            }
        } else {
            log.info("FAILLLLLLL");
            return "Fail";
        }
    }

    public String findUserPw(FindUserPasswordDto request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            Optional<Member> member = memberRepository.findByEmail(request.getEmail());
            if (member.get().getUserCheckQuestionNumber() == request.getUserCheckQuestionNumber()) {
                if (member.get().getUserCheckQuestionAnswer().equals(request.getUserCheckQuestionAnswer())) {
                    String tempPw = randomPw();
                    updateUserInfo(request.getEmail(),tempPw);
                    log.info("tmep password = {}",tempPw);
                    return tempPw;
                }
                else {
                    log.info("FAILLLLLLL");
                    return "Fail";
                }
            } else {
                log.info("FAILLLLLLL");
                return "Fail";
            }
        } else {
            log.info("FAILLLLLLL");
            return "Fail";
        }
    }

    public String changeUserPw(ChangePwDto request) {
        if (request.getNewPassword().equals(request.getCheckNewPassword()))
        {
            if (updateUserInfo(request.getEmail(),request.getNewPassword()).equals("Success"))
            {
                return "Success";
            }
            else {
                log.info("errorororororo");
                return "오류가 발생하였습니다.";
            }
        }
        else {
            log.info("비밀번호를 확인해주세요");
            return "비밀번호를 확인해주세요";
        }
    }

    public static String randomPw(){
        char pwCollection[] = new char[] {
                '1','2','3','4','5','6','7','8','9','0',
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};//배열에 선언

        String ranPw = "";

        for (int i = 0; i < 10; i++) {
            int selectRandomPw = (int)(Math.random()*(pwCollection.length));//Math.rondom()은 0.0이상 1.0미만의 난수를 생성해 준다.
            ranPw += pwCollection[selectRandomPw];
        }
        return ranPw;
    }

    public String updateUserInfo(String userEmail, String PW){
        Optional<Member> temp = memberRepository.findByEmail(userEmail);

        member.setId(temp.get().getId());
        member.setName(temp.get().getName());
        member.setEmail(temp.get().getEmail());
        member.setPassword(PW);
        member.setUserCheckQuestionNumber(temp.get().getUserCheckQuestionNumber());
        member.setUserCheckQuestionAnswer(temp.get().getUserCheckQuestionAnswer());

        memberRepository.save(member);

        return "Success";
    }
}

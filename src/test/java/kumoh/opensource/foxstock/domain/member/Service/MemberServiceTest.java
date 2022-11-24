package kumoh.opensource.foxstock.domain.member.Service;

import kumoh.opensource.foxstock.domain.member.Dto.ChangePwDto;
import kumoh.opensource.foxstock.domain.member.Dto.FindUserIdDto;
import kumoh.opensource.foxstock.domain.member.Dto.FindUserPasswordDto;
import kumoh.opensource.foxstock.domain.member.Dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;


    @Test
    void signup() {
        MemberDto request1 = new MemberDto();

        request1.setName("asdasdsa");
        request1.setEmail("idid");
        request1.setPassword("asdasd");
        request1.setCheckPassword("asdasd");
        request1.setUserCheckQuestionNumber(1);
        request1.setUserCheckQuestionAnswer(".");

        memberService.signup(request1);
    }

    @Test
    void login() {
        MemberDto request1 = new MemberDto();
        request1.setEmail("idid");
        request1.setPassword("asdasd");

        memberService.login(request1);
    }

    @Test
    void findID() {
        FindUserIdDto request1 = new FindUserIdDto();
        request1.setName("asdasdsa");
        request1.setUserCheckQuestionNumber(1);
        request1.setUserCheckQuestionAnswer(".");

        memberService.findUserId(request1);
    }

    @Test
    void findPw() {
        FindUserPasswordDto request1 = new FindUserPasswordDto();
        request1.setEmail("idid");
        request1.setUserCheckQuestionNumber(1);
        request1.setUserCheckQuestionAnswer(".");

        memberService.findUserPw(request1);
    }

    @Test
    void chPw() {
        ChangePwDto request1 = new ChangePwDto();
        request1.setEmail("idid");
        request1.setNowPassword("NkJ3GcMufe");
        request1.setNewPassword(".");
        request1.setCheckNewPassword(".");

        memberService.changeUserPw(request1);
    }
}
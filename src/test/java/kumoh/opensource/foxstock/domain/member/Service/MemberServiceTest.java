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

        request1.setName("jy");
        request1.setEmail("dfdsfsdf");
        request1.setPassword("asdasd");
        request1.setCheckPassword("asdasd");
        request1.setUserCheckQuestionNumber(4);
        request1.setUserCheckQuestionAnswer("gjfg56hrt");

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
    void findPW() {
        FindUserPasswordDto request1 = new FindUserPasswordDto();
        request1.setEmail("idid");
        request1.setUserCheckQuestionNumber(1);
        request1.setUserCheckQuestionAnswer(".");

        memberService.findUserPw(request1);
    }

    @Test
    void changePW() {
        ChangePwDto request1 = new ChangePwDto();
        request1.setEmail("idid");
        request1.setNewPassword("hgdfg");
        request1.setCheckNewPassword("hgdfg");

        memberService.changeUserPw(request1);
    }
}
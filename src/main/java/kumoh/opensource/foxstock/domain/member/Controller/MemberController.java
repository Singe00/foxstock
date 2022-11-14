package kumoh.opensource.foxstock.domain.member.Controller;

import kumoh.opensource.foxstock.domain.member.Dto.*;
import kumoh.opensource.foxstock.domain.member.Service.InterestService;
import kumoh.opensource.foxstock.domain.member.Service.MemberService;
import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final InterestService interestService;

    @PostMapping("/signUp")
    @ResponseBody
    public boolean signup(@RequestBody MemberDto request) {
        log.info("userName = {}, userId = {}, password = {}, userCheckQuestionNumber = {}, userCheckQuestionAnswer = {}"
                ,request.getName(), request.getEmail(), request.getPassword(),request.getUserCheckQuestionNumber(),request.getUserCheckQuestionAnswer());
        if(memberService.signup(request).equals("Success")) {
            return true;
        }
        return false;
    }

    @PostMapping("/login")
    @ResponseBody
    public boolean login(@RequestBody MemberDto request) {
        log.info("userId = {}, password = {}", request.getEmail(), request.getPassword());

        if(memberService.login(request).equals("Success")) {
            return true;
        }

        return false;
    }

    @PostMapping("/findId")
    @ResponseBody
    public String findUserId(@RequestBody FindUserIdDto request) {
        log.info("userName = {}, userCheckQuestionNumber={}, userCheckQuestionAnswer={}",
                request.getName(),request.getUserCheckQuestionNumber(),request.getUserCheckQuestionAnswer());
        String result = memberService.findUserId(request);
        if (result.equals("Fail"))
        {
            return "입력 정보를 찾을 수 없습니다.";
        }
        return result;
    }

    @PostMapping("/findPw")
    @ResponseBody
    public String findUserPw(@RequestBody FindUserPasswordDto request) {
        log.info("userEmail = {}, userCheckQuestionNumber={}, userCheckQuestionAnswer={}",
                request.getEmail(),request.getUserCheckQuestionNumber(),request.getUserCheckQuestionAnswer());
        String result = memberService.findUserPw(request);
        if (result.equals("Fail"))
        {
            return "입력 정보를 찾을 수 없습니다.";
        }
        return result;
    }

    @PostMapping("/changePw")
    @ResponseBody
    public boolean changePw(@RequestBody ChangePwDto request) {
        String result = memberService.changeUserPw(request);
        if (result.equals("Success"))
        {
            return true;
        }
        return false;
    }

    @PostMapping("/addInterest")
    @ResponseBody
    public boolean addInterest(@RequestBody InterestDto request) {
        String result = interestService.addInterest(request);
        if (result.equals("Success"))
        {
            return true;
        }
        return false;
    }

    @PostMapping("/deleteInterest")
    @ResponseBody
    public boolean deleteInterest(@RequestBody InterestDto request) {
        String result = interestService.deleteInterest(request);

        if (result.equals("Success"))
        {
            return false;
        }
        return true;
    }

    @PostMapping("/returnInterest")
    @ResponseBody
    public List<Stock> returnInterest(@RequestBody InterestDto request) {
        return interestService.returnInterest(request);
    }
}

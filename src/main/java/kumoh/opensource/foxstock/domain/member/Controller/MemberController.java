package kumoh.opensource.foxstock.domain.member.Controller;

import kumoh.opensource.foxstock.domain.member.Dto.MemberDto;
import kumoh.opensource.foxstock.domain.member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody MemberDto request) {
        log.info("userId = {}, password = {}", request.getUserId(), request.getPassword());
        if(memberService.signup(request).equals("Success")) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberDto request) {
        log.info("userId = {}, password = {}", request.getUserId(), request.getPassword());
        if(memberService.login(request).equals("Success")) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}

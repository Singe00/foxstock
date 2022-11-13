package kumoh.opensource.foxstock.domain.member.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String name;
    private String email;
    private String password;
    private String checkPassword;
    private int userCheckQuestionNumber;
    private String userCheckQuestionAnswer;
}
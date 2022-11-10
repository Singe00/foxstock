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
    private String userName;
    private String userId;
    private String password;
    private int userCheckQuestionNumber;
    private String userCheckQuestionAnswer;
}
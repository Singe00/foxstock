package kumoh.opensource.foxstock.domain.member.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindUserPasswordDto {
    private String userId;
    private int userCheckQuestionNumber;
    private String userCheckQuestionAnswer;
}
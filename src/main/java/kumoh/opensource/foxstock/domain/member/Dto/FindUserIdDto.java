package kumoh.opensource.foxstock.domain.member.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindUserIdDto {
    private String name;
    private int userCheckQuestionNumber;
    private String userCheckQuestionAnswer;
}
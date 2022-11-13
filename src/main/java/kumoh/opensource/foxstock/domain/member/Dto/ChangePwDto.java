package kumoh.opensource.foxstock.domain.member.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePwDto {
    private String email;
    private String newPassword;
    private String checkNewPassword;
}

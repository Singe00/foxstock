package kumoh.opensource.foxstock.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CodeDto {

    @Id
    private String srtnCd; //종목코드
    private String itmsNm; //종목이름
    private String crno; //법인등록번호

}

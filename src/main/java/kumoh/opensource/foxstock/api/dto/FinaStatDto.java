package kumoh.opensource.foxstock.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FinaStatDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String crno; //법인등록번호
    String bizYear; // 영업연도
    String enpCrtmNpf; // 당기순이익
    String enpTcptAmt; // 총자본
    String fnclDcdNm; //재무제표 타입

}

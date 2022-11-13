package kumoh.opensource.foxstock.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PriceDto {
    @Id
    String srtnCd; //종목코드
    String clpr; //현재가격
    String lstgStCnt; // 주식수
    String mrktCtg; //KOPSI.KOSDAQ

}

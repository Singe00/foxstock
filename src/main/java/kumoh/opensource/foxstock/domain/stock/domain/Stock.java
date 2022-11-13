package kumoh.opensource.foxstock.domain.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    @Id
    String srtnCd;
    String crno;
    String name;
    Integer currentPrice;
    String mrktCtg;
    Long lstgStCnt;
    Long capital;
    Integer bps;
    Double pbr;
    Double averageRoe;
    Double expectedReturn;
    Integer purchasePrice;

}

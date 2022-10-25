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
    Double expectedReturn;
    Integer purchasePrice;
    int currentPrice;
    long lstgStCnt;

}

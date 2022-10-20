package kumoh.opensource.foxstock.domain.stock.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Stock {

    @Id
    String srtnCd;
    String name;
    double expectedReturn;
    int purchasePrice;

}

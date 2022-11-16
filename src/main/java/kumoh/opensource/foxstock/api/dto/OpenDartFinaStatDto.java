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
public class OpenDartFinaStatDto {

    @Id
    String corpCode;
    String coprName;
    Long firstCapital;
    Long secondCapital;
    Long thirdCapital;
    Long firstProfit;
    Long secondProfit;
    Long thirdProfit;
}

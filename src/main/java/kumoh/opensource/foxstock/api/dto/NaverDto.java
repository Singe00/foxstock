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
public class NaverDto {

    @Id
    String srtnCd;
    Double firstRoe;
    Double secondRoe;
    Double thirdRoe;
    Integer bps;
}

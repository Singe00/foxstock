package kumoh.opensource.foxstock.domain.member.Domain;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interest")
@Getter
@Setter
@Entity
@Builder
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "srtn_cd", nullable = false)
    private String srtnCd;

}

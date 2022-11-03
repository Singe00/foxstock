package kumoh.opensource.foxstock.domain.member.Domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Getter
@Setter
@Entity
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

}

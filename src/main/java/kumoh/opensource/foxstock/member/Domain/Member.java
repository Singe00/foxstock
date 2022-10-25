package kumoh.opensource.foxstock.member.Domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Table
@Getter
@Setter
@Entity
@Builder
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

}

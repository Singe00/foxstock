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

    @Column(name = "user_name",nullable = false)
    private String name;

    @Column(name = "user_id", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_check_question_number",nullable = false)
    private int userCheckQuestionNumber;

    @Column(name = "user_check_question_answer",nullable = false)
    private String userCheckQuestionAnswer;
}

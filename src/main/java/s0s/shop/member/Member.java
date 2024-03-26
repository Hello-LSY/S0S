package s0s.shop.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 50)
    private String nickname;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 10)
    private String gender;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    Member(String username, String password, String nickname, String email, String gender, Role role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.role = role;
    }
}

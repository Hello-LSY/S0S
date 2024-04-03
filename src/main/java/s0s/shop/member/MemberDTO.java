package s0s.shop.member;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class MemberDTO {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String gender;
}

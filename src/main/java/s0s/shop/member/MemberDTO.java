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

    @Builder
    public MemberDTO(String username, String password, String nickname, String email, String gender) {

        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }


}

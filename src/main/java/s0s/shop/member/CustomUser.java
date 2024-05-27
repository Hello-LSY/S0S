package s0s.shop.member;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class CustomUser extends User {

    private final String nickname;

    public CustomUser(String username, String password, List<GrantedAuthority> authorities, String nickname){
        super(username, password, authorities);
        this.nickname = nickname;
    }

}

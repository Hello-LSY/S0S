package s0s.shop.member;

import org.springframework.security.core.userdetails.UserDetails;

public interface MemberService {

    public void join(MemberDTO memberDTO);

}

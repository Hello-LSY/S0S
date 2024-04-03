package s0s.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void join(MemberDTO memberDTO) {
        if(memberDTO==null){
            throw new IllegalArgumentException("회원 정보가 유효하지 않습니다.");
        }

        Role role = Role.USER;
        if(memberDTO.getUsername().equals("admin")){
            role = Role.ADMIN;
        }

        Member member = Member.builder()
                .username(memberDTO.getUsername())
                //비밀번호 암호화하기
                .password(memberDTO.getPassword())
                .nickname(memberDTO.getNickname())
                .email(memberDTO.getEmail())
                .gender(memberDTO.getGender())
                .role(role)
                .build();

        validateDuplicateMember(member);
        memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){
        memberRepository.findByUsername(member.getUsername())
                .ifPresent(findMember -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }

}
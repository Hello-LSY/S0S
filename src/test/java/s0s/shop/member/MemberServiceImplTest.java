package s0s.shop.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 테스트")
    void join_Success() {
        // Given
        MemberDTO memberDTO = MemberDTO.builder()
                .username("testUser")
                .password("testPassword")
                .nickname("testNickname")
                .gender("male")
                .build();
        when(memberRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        // When
        memberService.join(memberDTO);

        // Then
        verify(memberRepository, times(1)).save(any(Member.class));
    }
}
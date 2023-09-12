package com.example.bankspringsecurity.service;

import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRepository;
import com.example.bankspringsecurity.domain.user.UserRole;
import com.example.bankspringsecurity.dto.JoinRequest;
import com.example.bankspringsecurity.dto.JoinResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    // 진짜 객체를 Mock 객체에 넣기 위해서는 Spy 사용 필요
    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 테스트")
    void signUpTest() {
        // given
        JoinRequest request = new JoinRequest("kim", "1234", "kim@naver.com", "김");
        User user = User.builder()
                .id(1L)
                .username("kim")
                .password("1234")
                .email("kim@naver.com")
                .fullName("김")
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(userRepository.findByUsername(any()))
                .willReturn(Optional.empty());

        given(userRepository.save(any()))
                .willReturn(user);

        // when
        JoinResponse got = userService.signUp(request);

        // then
        assertThat(got.id()).isEqualTo(1L);
    }
}
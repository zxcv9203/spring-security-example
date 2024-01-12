package com.example.bankspringsecurity.config.jwt;

import com.example.bankspringsecurity.config.auth.LoginUser;
import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRole;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProcessTest {

    @Test
    void createTest() {
        // when
        String jwtToken = createToken();

        // then
        assertThat(jwtToken).startsWith(JwtVO.TOKEN_PREFIX);
    }

    @Test
    void verifyTest() {
        // given
        String token = createToken();
        String jwtToken = token.replace(JwtVO.TOKEN_PREFIX, "");

        // when
        LoginUser loginUser = JwtProcess.verify(jwtToken);

        // then
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
    }

    private String createToken() {
        User user = User.builder()
                .id(1L)
                .role(UserRole.CUSTOMER)
                .build();
        LoginUser loginUser1 = new LoginUser(user);

        return JwtProcess.create(loginUser1);
    }
}
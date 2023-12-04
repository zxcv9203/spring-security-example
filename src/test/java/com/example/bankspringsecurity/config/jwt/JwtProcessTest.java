package com.example.bankspringsecurity.config.jwt;

import com.example.bankspringsecurity.config.auth.LoginUser;
import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRole;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProcessTest {

    @Test
    void createTest() {
        // given
        User user = User.builder()
                .id(1L)
                .role(UserRole.CUSTOMER)
                .build();
        LoginUser loginUser = new LoginUser(user);

        // when
        String jwtToken = JwtProcess.create(loginUser);

        // then
        assertThat(jwtToken.startsWith(JwtVO.TOKEN_PREFIX)).isTrue();
    }

    @Test
    void verifyTest() {
        // given
        String jwtToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJiYW5rIiwiZXhwIjoxNzAyMzIwNDU4LCJpZCI6MSwicm9sZSI6IkNVU1RPTUVSIn0.QEdRw8H6_gY0eeED4_2HLg1EtQJ4xiBC5EP78jLxjiN5j2jbAzPEswETM6P4cnjeDiNMFgFnvnyaiya6q1aoeQ";

        // when
        LoginUser loginUser = JwtProcess.verify(jwtToken);

        // then
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
    }
}
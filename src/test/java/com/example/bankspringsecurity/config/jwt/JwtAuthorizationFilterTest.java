package com.example.bankspringsecurity.config.jwt;

import com.example.bankspringsecurity.config.auth.LoginUser;
import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRole;
import com.example.bankspringsecurity.stub.UserStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JwtAuthorizationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[성공] 토큰을 헤더에 담아 인증이 필요한 URL로 요청보낼 경우 404 반횐(존재하지 않는 URL로 요청)")
    void successAuthorizationTest() throws Exception {
        // given
        User user = UserStub.create(1L, "test", "test");
        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/s/hello/test")
                        .header(JwtVO.HEADER, jwtToken)
        );

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("[성공] 토큰을 헤더에 담아 어드민 인증이 필요한 URL로 요청보낼 경우 404 반횐")
    void successAdminAuthorizationTest() throws Exception {
        // given
        User user = UserStub.create(1L, "test", "test");
        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/admin/hello/test")
                        .header(JwtVO.HEADER, jwtToken)
        );

        // then
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("[실패] 어드민 권한이 아닌 사용자가 어드민 권한 API 요청시 403 반환")
    void failAdminAuthorizationTest() throws Exception {
        // given
        User user = User.builder().id(1L).role(UserRole.ADMIN).build();
        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/admin/hello/test")
                        .header(JwtVO.HEADER, jwtToken)
        );

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("[실패] 인증되지 않은 사용자의 경우 401 반환")
    void failAuthorizationTest() throws Exception{
        // given

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/s/hello/test")
        );

        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}
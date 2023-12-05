package com.example.bankspringsecurity.config.jwt;


import com.example.bankspringsecurity.domain.user.UserRepository;
import com.example.bankspringsecurity.dto.LoginRequest;
import com.example.bankspringsecurity.stub.UserStub;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JwtAuthenticationFilterTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(UserStub.create(1L, "test", "test"));
    }

    @Test
    void successfulAuthenticationTest() throws Exception {
        // given
        LoginRequest request = LoginRequest.builder()
                .username("test")
                .password("1234")
                .build();
        String requestBody = mapper.writeValueAsString(request);


        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(requestBody)
        );
        String jwtToken = resultActions.andReturn()
                .getResponse()
                .getHeader(JwtVO.HEADER);

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("test"));
        assertThat(jwtToken).isNotNull()
                .startsWith(JwtVO.TOKEN_PREFIX);
    }

    @Test
    @DisplayName("[실패] 비밀번호를 잘못 입력시 로그인이 실패되어야 합니다.")
    void unSuccessfulAuthenticationTest() throws Exception{
        // given
        LoginRequest request = LoginRequest.builder()
                .username("test")
                .password("12345")
                .build();
        String requestBody = mapper.writeValueAsString(request);


        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(requestBody)
        );
        String jwtToken = resultActions.andReturn()
                .getResponse()
                .getHeader(JwtVO.HEADER);

        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}
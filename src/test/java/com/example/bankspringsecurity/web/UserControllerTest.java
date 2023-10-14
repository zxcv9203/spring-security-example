package com.example.bankspringsecurity.web;

import com.example.bankspringsecurity.dto.JoinRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[성공] 회원가입 테스트")
    void success_join_test() throws Exception {
        // given
        JoinRequest request = JoinRequest.builder()
                .username("zxcv9203")
                .password("1234")
                .email("zxcv9203@naver.com")
                .fullName("김용철")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/join")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("[실패] 회원가입시 잘못된 요청이 들어올 경우 예외 발생")
    void fail_join_test() throws Exception {
        // given
        JoinRequest request = JoinRequest.builder()
                .username("zxcv9203임")
                .password("1234")
                .email("zxcv9203@naver.com")
                .fullName("김용철")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/join")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }
}
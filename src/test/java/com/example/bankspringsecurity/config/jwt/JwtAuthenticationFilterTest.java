package com.example.bankspringsecurity.config.jwt;


import com.example.bankspringsecurity.domain.user.UserRepository;
import com.example.bankspringsecurity.dto.LoginRequest;
import com.example.bankspringsecurity.stub.UserStub;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
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
        String responseBody = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
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
    void unSuccessfulAuthenticationTest() {
        // given

        // when

        // then
    }
}
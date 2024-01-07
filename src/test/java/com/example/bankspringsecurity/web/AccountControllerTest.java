package com.example.bankspringsecurity.web;

import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRepository;
import com.example.bankspringsecurity.dto.AccountSaveRequest;
import com.example.bankspringsecurity.stub.UserStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = UserStub.create(1L, "kim", "kim");
        userRepository.save(user);
    }

    @Test
    @WithUserDetails(value = "kim", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void saveAccountTest() throws Exception {
        // given
        AccountSaveRequest request = new AccountSaveRequest(9999L, 1234L);
        String requestBody = mapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mvc.perform(post("/api/s/accounts")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        );
        String response = resultActions.andReturn().getResponse().getContentAsString();

        // then
        resultActions.andExpect(status().isCreated());
    }
}
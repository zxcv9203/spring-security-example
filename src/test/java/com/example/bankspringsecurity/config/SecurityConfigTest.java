package com.example.bankspringsecurity.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc // Mock 환경에 MockMvc가 등록됨
class SecurityConfigTest {

    // 가짜 환경에 등록된 MockMvc를 DI함
    @Autowired
    private MockMvc mockMvc;

    // 서버는 일관성 있게 에러가 리턴되어야 한다.
    // 내가 모르는 에러가 프론트한테 날라가지 않도록 직접 제어하기
    @Test
    void authentication() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/s/hello"));
        String responseBody = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
        int httpStatusCode = resultActions.andReturn()
                .getResponse()
                .getStatus();

        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + httpStatusCode);

        // then
        assertThat(httpStatusCode).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void authorization() throws Exception{
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/admin/hello"));
        String responseBody = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
        int httpStatusCode = resultActions.andReturn()
                .getResponse()
                .getStatus();

        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + httpStatusCode);

        // then
        assertThat(httpStatusCode).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
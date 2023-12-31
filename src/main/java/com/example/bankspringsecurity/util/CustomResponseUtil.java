package com.example.bankspringsecurity.util;

import com.example.bankspringsecurity.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class CustomResponseUtil {

    public static void success(HttpServletResponse response, Object dto) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ApiResponse<?> apiResponse = new ApiResponse<>(1, "로그인 성공", dto);

            String responseBody = mapper.writeValueAsString(apiResponse);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

    public static void fail(HttpServletResponse response, String msg, HttpStatus status) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ApiResponse<?> apiResponse = new ApiResponse<>(-1, msg, null);

            String responseBody = mapper.writeValueAsString(apiResponse);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(status.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

}

package com.example.bankspringsecurity.util;

import com.example.bankspringsecurity.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class CustomResponseUtil {
    public static void unAuthentication(HttpServletResponse response, String msg) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(-1, msg, null);

            String responseBody = mapper.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(401);
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }
}

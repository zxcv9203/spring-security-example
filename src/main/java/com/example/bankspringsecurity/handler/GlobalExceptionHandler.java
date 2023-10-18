package com.example.bankspringsecurity.handler;

import com.example.bankspringsecurity.dto.ApiResponse;
import com.example.bankspringsecurity.exception.CustomApiException;
import com.example.bankspringsecurity.exception.CustomForbiddenException;
import com.example.bankspringsecurity.exception.CustomValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(CustomApiException e) {
        log.error(e.getMessage());

        return new ResponseEntity<>(
                new ApiResponse<>(-1, e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CustomForbiddenException.class)
    public ResponseEntity<ApiResponse<Void>> handleForbiddenException(CustomForbiddenException e) {
        log.error(e.getMessage());

        return new ResponseEntity<>(
                new ApiResponse<>(-1, e.getMessage(), null),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(CustomValidationException e) {
        log.error(e.getMessage());

        return new ResponseEntity<>(
                new ApiResponse<>(-1, e.getMessage(), e.getErrorMap()),
                HttpStatus.BAD_REQUEST
        );
    }
}

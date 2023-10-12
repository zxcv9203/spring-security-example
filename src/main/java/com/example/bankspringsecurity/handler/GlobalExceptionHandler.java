package com.example.bankspringsecurity.handler;

import com.example.bankspringsecurity.dto.ResponseDto;
import com.example.bankspringsecurity.exception.CustomApiException;
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
    public ResponseEntity<ResponseDto<Void>> handleApiException(CustomApiException e) {
        log.error(e.getMessage());

        return new ResponseEntity<>(
                new ResponseDto<>(-1, e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleValidationException(CustomValidationException e) {
        log.error(e.getMessage());

        return new ResponseEntity<>(
                new ResponseDto<>(-1, e.getMessage(), e.getErrorMap()),
                HttpStatus.BAD_REQUEST
        );
    }
}

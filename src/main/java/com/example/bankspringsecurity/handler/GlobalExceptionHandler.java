package com.example.bankspringsecurity.handler;

import com.example.bankspringsecurity.dto.ResponseDto;
import com.example.bankspringsecurity.exception.CustomApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<ResponseDto<Void>> handleInternalServerError(CustomApiException e) {
        log.error(e.getMessage());

        return new ResponseEntity<>(
                new ResponseDto<>(null, null, null),
                HttpStatus.BAD_REQUEST
        );
    }
}

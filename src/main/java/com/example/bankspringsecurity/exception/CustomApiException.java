package com.example.bankspringsecurity.exception;

public class CustomApiException extends RuntimeException {
    public CustomApiException(String message) {
        super(message);
    }
}

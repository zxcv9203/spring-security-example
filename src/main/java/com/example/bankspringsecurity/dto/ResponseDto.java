package com.example.bankspringsecurity.dto;

/**
 * @param code 1 성공, -1 실패
 */
public record ResponseDto<T>(Integer code, String message, T data) {
}

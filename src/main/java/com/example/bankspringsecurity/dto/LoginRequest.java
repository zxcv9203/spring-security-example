package com.example.bankspringsecurity.dto;

public record LoginRequest(
        String username,
        String password
) {
}

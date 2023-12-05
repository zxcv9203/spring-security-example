package com.example.bankspringsecurity.dto;

import lombok.Builder;

@Builder
public record LoginRequest(
        String username,
        String password
) {
}

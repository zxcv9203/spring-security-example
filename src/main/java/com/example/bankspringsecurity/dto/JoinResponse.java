package com.example.bankspringsecurity.dto;

public record JoinResponse(
        Long id,
        String username,
        String fullName
) {
}

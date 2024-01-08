package com.example.bankspringsecurity.dto;

public record AccountResponse(
        Long id,
        Long number,
        Long balance
) {
}

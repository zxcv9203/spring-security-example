package com.example.bankspringsecurity.dto;

import lombok.Builder;

public record AccountDepositResponse(
        Long id,
        Long number,
        TransactionResponse transaction
) {
    @Builder
    public AccountDepositResponse {}
}

package com.example.bankspringsecurity.dto;

import com.example.bankspringsecurity.domain.account.Account;
import lombok.Builder;

@Builder
public record AccountSaveResponse(
    Long id,
    Long number,
    Long balance
) {
    public static AccountSaveResponse from(Account account) {
        return AccountSaveResponse.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .number(account.getNumber())
                .build();
    }
}

package com.example.bankspringsecurity.dto;

import com.example.bankspringsecurity.domain.account.Account;
import com.example.bankspringsecurity.domain.user.User;
import lombok.Builder;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Builder
public record AccountSaveRequest(
        @NotNull
        @Digits(integer = 4, fraction = 4)
        Long number,

        @NotNull
        @Digits(integer = 4, fraction = 4)
        Long password
) {

    public Account toEntity(User user) {
        return Account.builder()
                .number(number)
                .password(password)
                .balance(1000L)
                .user(user)
                .build();
    }
}

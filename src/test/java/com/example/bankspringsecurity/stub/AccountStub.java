package com.example.bankspringsecurity.stub;

import com.example.bankspringsecurity.domain.account.Account;
import com.example.bankspringsecurity.domain.user.User;

import java.time.LocalDateTime;

public class AccountStub {
    private AccountStub() {}

    public static Account newAccount(Long number, User user) {
        return Account.builder()
                .id(1L)
                .number(number)
                .password(1234L)
                .balance(1L)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

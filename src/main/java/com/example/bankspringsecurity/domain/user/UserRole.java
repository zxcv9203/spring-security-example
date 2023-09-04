package com.example.bankspringsecurity.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
    ADMIN("관리자"),
    CUSTOMER("고객");

    private final String name;
    }

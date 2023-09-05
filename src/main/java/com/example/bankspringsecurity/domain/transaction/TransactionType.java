package com.example.bankspringsecurity.domain.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TransactionType {
    WITHDRAW("출금"),
    DEPOSIT("입금"),
    TRANSFER("이체"),
    ALL("입출금 내역");

    private final String type;
}

package com.example.bankspringsecurity.stub;

import com.example.bankspringsecurity.domain.account.Account;
import com.example.bankspringsecurity.domain.transaction.Transaction;
import com.example.bankspringsecurity.domain.transaction.TransactionType;

import java.time.LocalDateTime;

public class TransactionStub {

    private TransactionStub() {}

    public static Transaction createDeposit(Long id, Account account) {
        return Transaction.builder()
                .id(id)
                .depositAccount(account)
                .withdrawAccount(null)
                .depositAccountBalance(account.getBalance() + 100L)
                .withdrawAccountBalance(null)
                .amount(100L)
                .type(TransactionType.DEPOSIT)
                .sender("ATM")
                .receiver(account.getNumber() + "")
                .phoneNumber("01012345678")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

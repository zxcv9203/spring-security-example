package com.example.bankspringsecurity.dto;

import com.example.bankspringsecurity.domain.transaction.Transaction;
import com.example.bankspringsecurity.util.CustomDateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.ToString;

public record TransactionResponse(
        Long id,
        String type,
        String sender,
        String receiver,
        Long amount,
        @JsonIgnore
        Long depositAccountBalance,
        String phoneNumber,
        String createdAt
) {
    @Builder
    public TransactionResponse {}
    public static TransactionResponse convertResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .type(transaction.getType().toString())
                .sender(transaction.getSender())
                .receiver(transaction.getReceiver())
                .amount(transaction.getAmount())
                .depositAccountBalance(transaction.getDepositAccountBalance())
                .phoneNumber(transaction.getPhoneNumber())
                .createdAt(CustomDateUtil.toStringFormat(transaction.getCreatedAt()))
                .build();
    }
}

package com.example.bankspringsecurity.dto;

import java.util.List;

public record AccountsResponse(
        String fullName,
        List<AccountResponse> accounts
) {
}

package com.example.bankspringsecurity.dto;

import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.util.CustomDateUtil;
import lombok.Builder;

public record LoginResponse(
        Long id,
        String username,
        String createdAt
) {
    public static LoginResponse from(User user) {
        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                CustomDateUtil.toStringFormat(user.getCreatedAt())
        );
    }
}

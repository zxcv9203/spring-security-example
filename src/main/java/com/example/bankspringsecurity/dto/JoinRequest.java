package com.example.bankspringsecurity.dto;

import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;

public record JoinRequest(
        @NotEmpty
        String username,
        @NotEmpty
        String password,
        @NotEmpty
        String email,
        @NotEmpty
        String fullName) {
    
    public User toEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .fullName(fullName)
                .role(UserRole.CUSTOMER)
                .build();
    }
}

package com.example.bankspringsecurity.stub;

import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class UserStub {
    private UserStub() {

    }

    public static User create(Long id, String username, String fullName) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("1234");
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(username + "@naver.com")
                .fullName(fullName)
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

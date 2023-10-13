package com.example.bankspringsecurity.dto;

import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record JoinRequest(
        // 영문 + 숫자, 길이 최소 2 ~ 20자 이내, 공백 + null X
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2 ~ 20자 이내로 작성해주세요.")
        @NotEmpty
        String username,

        // 길이 4 ~ 20
        @Size(min = 4, max = 20, message = "비밀번호는 4 ~ 20자 이내 여야 합니다.")
        @NotEmpty
        String password,

        // 이메일 형식
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", message = "유효한 이메일 형식을 입력해주세요.")
        @NotEmpty
        String email,

        // 영어, 한글, 1 ~ 20
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1 ~ 20자 이내로 작성해주세요.")
        @NotEmpty
        String fullName
) {
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

package com.example.bankspringsecurity.service;

import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRepository;
import com.example.bankspringsecurity.dto.JoinRequest;
import com.example.bankspringsecurity.dto.JoinResponse;
import com.example.bankspringsecurity.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public JoinResponse signUp(JoinRequest request) {
        // 1. 동일 유저 네임 존재 검사
        Optional<User> duplicateUser = userRepository.findByUsername(request.username());
        if (duplicateUser.isPresent()) {
            throw new CustomApiException("동일한 usename이 존재합니다.");
        }

        // 2. 패스워드 인코딩 + 회원가입
        User user = userRepository.save(request.toEntity(passwordEncoder));


        // 3. dto 응답
        return new JoinResponse(user.getId(), user.getUsername(), user.getFullName());
    }
}

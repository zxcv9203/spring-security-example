package com.example.bankspringsecurity.web;

import com.example.bankspringsecurity.dto.JoinRequest;
import com.example.bankspringsecurity.dto.JoinResponse;
import com.example.bankspringsecurity.dto.ApiResponse;
import com.example.bankspringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<JoinResponse>> join(
            @RequestBody @Valid JoinRequest request,
            BindingResult bindingResult
    ) {
        JoinResponse response = userService.signUp(request);

        return new ResponseEntity<>(
                new ApiResponse<>(1, "회원가입 성공", response),
                HttpStatus.CREATED
        );
    }
}

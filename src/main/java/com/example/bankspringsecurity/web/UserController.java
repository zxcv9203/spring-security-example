package com.example.bankspringsecurity.web;

import com.example.bankspringsecurity.dto.JoinRequest;
import com.example.bankspringsecurity.dto.JoinResponse;
import com.example.bankspringsecurity.dto.ResponseDto;
import com.example.bankspringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinRequest request, BindingResult bindingResult) {
        JoinResponse response = userService.signUp(request);

        return new ResponseEntity<>(
                new ResponseDto<>(1, "회원가입 성공", response),
                HttpStatus.CREATED
        );
    }
}

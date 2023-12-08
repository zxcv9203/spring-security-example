package com.example.bankspringsecurity.web;

import com.example.bankspringsecurity.config.auth.LoginUser;
import com.example.bankspringsecurity.dto.AccountSaveRequest;
import com.example.bankspringsecurity.dto.AccountSaveResponse;
import com.example.bankspringsecurity.dto.ApiResponse;
import com.example.bankspringsecurity.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/s/accounts")
    public ResponseEntity<ApiResponse<AccountSaveResponse>> save(
            @RequestBody @Valid AccountSaveRequest request,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        AccountSaveResponse response = accountService.register(request, loginUser.getUser().getId());

        return new ResponseEntity<>(new ApiResponse<>(1, "계좌 등록 성공", response), HttpStatus.CREATED);
    }
}

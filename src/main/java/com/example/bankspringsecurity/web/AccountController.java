package com.example.bankspringsecurity.web;

import com.example.bankspringsecurity.config.auth.LoginUser;
import com.example.bankspringsecurity.dto.AccountSaveRequest;
import com.example.bankspringsecurity.dto.AccountSaveResponse;
import com.example.bankspringsecurity.dto.AccountsResponse;
import com.example.bankspringsecurity.dto.ApiResponse;
import com.example.bankspringsecurity.exception.CustomForbiddenException;
import com.example.bankspringsecurity.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

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

    @GetMapping("/s/accounts/login-user")
    public ResponseEntity<ApiResponse<AccountsResponse>> findUserAccount(
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        AccountsResponse accounts = accountService.findById(loginUser.getUser().getId());
        return new ResponseEntity<>(new ApiResponse<>(1, "계좌 목록보기(유저별) 성공", accounts), HttpStatus.OK);
    }

    @DeleteMapping("/s/accounts/{number}")
    public ResponseEntity<ApiResponse<Void>> deleteByNumber(
            @PathVariable Long number,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        accountService.deleteById(loginUser.getUser().getId(), number);
        return new ResponseEntity<>(new ApiResponse<>(1, "", null), HttpStatus.OK);
    }
}

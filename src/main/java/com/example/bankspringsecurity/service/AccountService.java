package com.example.bankspringsecurity.service;

import com.example.bankspringsecurity.domain.account.Account;
import com.example.bankspringsecurity.domain.account.AccountRepository;
import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRepository;
import com.example.bankspringsecurity.dto.AccountResponse;
import com.example.bankspringsecurity.dto.AccountSaveRequest;
import com.example.bankspringsecurity.dto.AccountSaveResponse;
import com.example.bankspringsecurity.dto.AccountsResponse;
import com.example.bankspringsecurity.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Transactional
    public AccountSaveResponse register(AccountSaveRequest request, Long userId) {
        // user가 db에 있는지 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("유저를 찾을 수 없습니다."));

        // 해당 계좌가 DB에 있는지 중복여부를 체크
        if (accountRepository.findByNumber(request.number()).isPresent()) {
            throw new CustomApiException("해당 계좌가 이미 존재합니다.");
        }

        // 계좌 등록
        Account account = accountRepository.save(request.toEntity(user));

        // DTO를 응답
        return AccountSaveResponse.from(account);
    }

    public AccountsResponse findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("유저를 찾을 수 없습니다."));

        // 유저의 모든 계좌목록
        List<AccountResponse> accounts = accountRepository.findByUser_Id(userId)
                .stream()
                .map(account -> new AccountResponse(account.getId(), account.getNumber(), account.getBalance()))
                .toList();

        return new AccountsResponse(user.getFullName(), accounts);
    }
}

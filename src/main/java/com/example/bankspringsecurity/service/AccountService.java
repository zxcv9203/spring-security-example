package com.example.bankspringsecurity.service;

import com.example.bankspringsecurity.domain.account.Account;
import com.example.bankspringsecurity.domain.account.AccountRepository;
import com.example.bankspringsecurity.domain.transaction.Transaction;
import com.example.bankspringsecurity.domain.transaction.TransactionRepository;
import com.example.bankspringsecurity.domain.transaction.TransactionType;
import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRepository;
import com.example.bankspringsecurity.dto.*;
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

    private final TransactionRepository transactionRepository;

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

    @Transactional
    public void deleteById(Long userId, Long number) {
        Account account = accountRepository.findByNumber(number)
                .orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다."));

        account.checkOwner(userId);

        accountRepository.deleteById(account.getId());
    }

    @Transactional
    public AccountDepositResponse deposit(AccountDepositRequest request) {
        // 0원 체크
        if (request.amount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }

        // 임금계좌 확인
        Account account = accountRepository.findByNumber(request.number())
                .orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다."));

        // 입금
        account.deposit(request.amount());

        // 거래 내역 남기기
        Transaction transaction = Transaction.builder()
                .depositAccount(account)
                .withdrawAccount(null)
                .depositAccountBalance(account.getBalance())
                .withdrawAccountBalance(null)
                .amount(request.amount())
                .type(TransactionType.DEPOSIT)
                .sender("ATM")
                .receiver(request.number() + "")
                .phoneNumber(request.phoneNumber())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return AccountDepositResponse.builder()
                .id(account.getId())
                .number(account.getNumber())
                .transaction(TransactionResponse.convertResponse(savedTransaction))
                .build();
    }
}

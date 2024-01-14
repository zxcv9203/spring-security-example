package com.example.bankspringsecurity.service;

import com.example.bankspringsecurity.domain.account.Account;
import com.example.bankspringsecurity.domain.account.AccountRepository;
import com.example.bankspringsecurity.domain.transaction.Transaction;
import com.example.bankspringsecurity.domain.transaction.TransactionRepository;
import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRepository;
import com.example.bankspringsecurity.dto.AccountDepositRequest;
import com.example.bankspringsecurity.dto.AccountDepositResponse;
import com.example.bankspringsecurity.dto.AccountSaveRequest;
import com.example.bankspringsecurity.dto.AccountSaveResponse;
import com.example.bankspringsecurity.stub.AccountStub;
import com.example.bankspringsecurity.stub.TransactionStub;
import com.example.bankspringsecurity.stub.UserStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void 계좌등록_test() {
        // given
        Long userId = 1L;
        AccountSaveRequest request = new AccountSaveRequest(1111L, 1234L);
        User user = UserStub.create(userId, "kim", "yongcheol");

        given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(user));
        given(accountRepository.findByNumber(anyLong()))
                .willReturn(Optional.empty());
        given(accountRepository.save(any()))
                .willReturn(AccountStub.newAccount(1111L, user));

        // when
        AccountSaveResponse response = accountService.register(request, userId);

        // then
        assertThat(response.number()).isEqualTo(1111L);
    }

    @Test
    void 계좌삭제_test() throws Exception {
        Long number = 1111L;
        Long userId = 1L;

        User user = UserStub.create(1L, "kim", "yongcheol-kim");
        Account account = AccountStub.newAccount(1L, user);
        given(accountRepository.findByNumber(any())).willReturn(Optional.of(account));

        accountService.deleteById(userId, number);

        verify(accountRepository, times(1)).deleteById(any());
    }

    @Test
    public void depositTest() {
        AccountDepositRequest request = new AccountDepositRequest(1111L, 100L, "DEPOSIT", "01012345678");

        User user = UserStub.create(1L, "kim", "kim");
        Account account = AccountStub.newAccount(1111L, user);

        given(accountRepository.findByNumber(any()))
                .willReturn(Optional.of(account));

        Transaction transaction = TransactionStub.createDeposit(1L, account);
        given(transactionRepository.save(any()))
                .willReturn(transaction);

        AccountDepositResponse got = accountService.deposit(request);

        assertThat(account.getBalance()).isEqualTo(101L);
        assertThat(got.transaction().depositAccountBalance()).isEqualTo(101L);
    }
}
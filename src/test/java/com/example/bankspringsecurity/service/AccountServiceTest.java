package com.example.bankspringsecurity.service;

import com.example.bankspringsecurity.domain.account.AccountRepository;
import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRepository;
import com.example.bankspringsecurity.dto.AccountSaveRequest;
import com.example.bankspringsecurity.dto.AccountSaveResponse;
import com.example.bankspringsecurity.stub.AccountStub;
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

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

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
        assertThat(request.number()).isEqualTo(1111L);
    }
}
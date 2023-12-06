package com.example.bankspringsecurity.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // TODO User가 현재 Lazy Loading이기 때문에 한번에 가져오도록 수정 필요
    Optional<Account> findByNumber(Long number);
}

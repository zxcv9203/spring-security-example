package com.example.bankspringsecurity.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
                    SELECT ac
                    FROM Account ac
                    JOIN FETCH ac.user u
                    WHERE ac.number = :number
            """)
    Optional<Account> findByNumber(Long number);

    List<Account> findByUser_Id(Long id);
}

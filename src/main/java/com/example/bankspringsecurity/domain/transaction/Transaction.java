package com.example.bankspringsecurity.domain.transaction;

import com.example.bankspringsecurity.domain.account.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account withdrawAccount;

    @ManyToOne
    private Account depositAccount;

    private Long amount;

    private Long withdrawAccountBalance;

    private Long depositAccountBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    // 계좌가 사라져도 로그는 남아야 한다.
    private String sender;
    private String receiver;
    private String phoneNumber;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Transaction(Long id, Account withdrawAccount, Account depositAccount, Long amount, Long withdrawAccountBalance, Long depositAccountBalance, TransactionType type, String sender, String receiver, String phoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.withdrawAccountBalance = withdrawAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

package com.example.bankspringsecurity.domain.account;

import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.exception.CustomApiException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 4)
    private Long number;    // 계좌 번호

    @Column(nullable = false, length = 4)
    private Long password;  // 계좌 비밀번호

    @Column(nullable = false)
    private Long balance;   // 잔액 (기본값 1000원)

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Account(Long id, Long number, Long password, Long balance, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void checkOwner(Long userId) {
        if (!Objects.equals(user.getId(), userId)) {
            throw new CustomApiException("계좌 소유자가 아닙니다.");
        }
    }
}

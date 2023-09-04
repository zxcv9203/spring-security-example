package com.example.bankspringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BankSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankSpringSecurityApplication.class, args);
    }

}

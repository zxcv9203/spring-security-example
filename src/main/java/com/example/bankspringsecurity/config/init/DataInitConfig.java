package com.example.bankspringsecurity.config.init;

import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRepository;
import com.example.bankspringsecurity.domain.user.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitConfig {
    @Profile("dev")
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return (args) -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode("1234");
            User user = User.builder()
                    .username("kim")
                    .password(password)
                    .email("kim" + "@naver.com")
                    .fullName("yongcheolkim")
                    .role(UserRole.CUSTOMER)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        };
    }
}

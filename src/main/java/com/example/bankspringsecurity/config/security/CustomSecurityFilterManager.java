package com.example.bankspringsecurity.config.security;

import com.example.bankspringsecurity.config.jwt.JwtAuthenticationFilter;
import com.example.bankspringsecurity.config.jwt.JwtAuthorizationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
        builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
        super.configure(builder);
    }
}
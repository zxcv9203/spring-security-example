package com.example.bankspringsecurity.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bankspringsecurity.config.auth.LoginUser;
import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRole;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtProcess {

    // 토큰 생성
    public static String create(LoginUser loginUser) {
        String jwtToken = JWT.create()
                .withSubject("bank")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRERATION_TIME))
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getRole().name())
                .sign(Algorithm.HMAC512(JwtVO.SECRET));

        return JwtVO.TOKEN_PREFIX + jwtToken;
    }

    // 토큰 검증 (return 되는 LoginUser 객체를 강제로 시큐리티 세션에 직접 주입)
    public static LoginUser verify(String token) {
        DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC512(JwtVO.SECRET))
                .build()
                .verify(token);
        Long id = decodedJwt.getClaim("id").asLong();
        String role = decodedJwt.getClaim("role").asString();

        User user = User.builder()
                .id(id)
                .role(UserRole.valueOf(role))
                .build();

        return new LoginUser(user);
    }
}

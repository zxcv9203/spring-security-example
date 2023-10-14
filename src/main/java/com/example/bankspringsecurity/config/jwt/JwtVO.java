package com.example.bankspringsecurity.config.jwt;

/**
 * SECRET은 노출되면 안됨 (클라우드 AWS에서나 환경변수, 파일에 있는 것을 읽어야 함)
 * refresh 토큰 (X)
 */
public interface JwtVO {
    String SECRET = "zxcv9203"; // HS256 (대칭키)
    int EXPIRERATION_TIME = 1000 * 60 * 60 * 24 * 7; // 일주일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER = "Authorization";

}

package com.example.spring.security.jwt.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefreshTokenService {
    public String createRefreshToken() {
        String refreshToken = null;
        refreshToken = UUID.randomUUID().toString();
        return refreshToken;
    }
}

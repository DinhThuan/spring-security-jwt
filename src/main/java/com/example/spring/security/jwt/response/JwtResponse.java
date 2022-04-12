package com.example.spring.security.jwt.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JwtResponse {
    private String accessToken;
    private Date expiredAccessToken;
    private String type;
    private String message;
    private List<String> roles;
    private String refreshToken;
    private String expiredRefreshToken;
}

package com.example.spring.security.jwt.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type;
    private String message;
}

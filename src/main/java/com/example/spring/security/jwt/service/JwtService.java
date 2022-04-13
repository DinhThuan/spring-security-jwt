package com.example.spring.security.jwt.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class JwtService {
    public static final String USERNAME = "username";
    public static String SECRET_KEY = "11111111111111111111111111111111";
    public static int EXPIRE_TIME = 10000;

    public String generateTokenLogin(String username) throws JOSEException {
        String token = null;

        JWSSigner signer = new MACSigner(generateShareSecret());
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        builder.claim(USERNAME, username);
        builder.expirationTime(generateExpirationDate());
        JWTClaimsSet claimsSet = builder.build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        signedJWT.sign(signer);

        token = signedJWT.serialize();

        return token;

    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }

    private byte[] generateShareSecret() {
        // Generate 256-bit (32-byte) shared secret
        byte[] sharedSecret = new byte[32];
        sharedSecret = SECRET_KEY.getBytes();
        return sharedSecret;
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            JWTClaimsSet claims = getClaimSFromToken(token);
            username = claims.getStringClaim(USERNAME);
        } catch (Exception e) {
            System.out.println("bug function getUsernameFromToken: " + e.getMessage());
        }

        return username;
    }

    private JWTClaimsSet getClaimSFromToken(String token) {
        JWTClaimsSet claims = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(generateShareSecret());
            if (signedJWT.verify(verifier)) {
                claims = signedJWT.getJWTClaimsSet();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return claims;
    }

    public Boolean validateTokenLogin(String token) {
        try {
            if (token == null || token.trim().length() == 0) {
                return false;
            }
            String username = getUsernameFromToken(token);
            if (username == null || username.isEmpty()) {
                return false;
            }
            if (isTokenExpired(token)) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Bug: " + e.getMessage());
        }

        return true;
    }

    private boolean isTokenExpired(String token) throws ParseException, JOSEException {
        Date expiration = getExpirationDateFromToken(token);
        System.out.println("Token is expired");
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) throws ParseException, JOSEException {
        Date expiration = null;
        JWTClaimsSet claims = getClaimSFromToken(token);
        expiration = claims.getExpirationTime();
        return expiration;
    }
}

package org.example.identityservice.Security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public abstract class Token {

    private static final String ACCESS_SECRET_KEY;

    static {
        try {
            ACCESS_SECRET_KEY = getAccessSecretKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static String generateToken(String name) throws NoSuchAlgorithmException {
        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .addClaims(new HashMap<>())
                .signWith(getSigningKey())
                .compact();
    }

    public static void isValidToken(String token) {
        try {
            getClaims(token);
        } catch (JwtException | NoSuchAlgorithmException e) {
            System.out.println("Can't get token");
            throw new JwtException("Can't get token");
        }
    }

    private static void getClaims(String token) throws NoSuchAlgorithmException {
        Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static String getAccessSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    private static Key getSigningKey() throws NoSuchAlgorithmException {
        return Keys.hmacShaKeyFor(ACCESS_SECRET_KEY.getBytes());
    }
}
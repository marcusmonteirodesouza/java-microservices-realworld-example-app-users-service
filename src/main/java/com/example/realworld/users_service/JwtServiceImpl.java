package com.example.realworld.users_service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtServiceImpl implements JwtService {
    private final Algorithm algorithm;

    public JwtServiceImpl(String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    public String makeToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuer("realworld.example.com")
                .sign(algorithm);
    }
}

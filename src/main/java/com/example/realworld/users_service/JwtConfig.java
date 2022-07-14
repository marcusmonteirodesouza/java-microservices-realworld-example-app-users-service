package com.example.realworld.users_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class JwtConfig {
    @Autowired
    private Environment env;

    @Bean
    JwtService jwtService() {
        return new JwtServiceImpl(env.getProperty("jwt.secret-key"));
    }
}

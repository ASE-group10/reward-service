package com.example.reward_service.utils;

import com.example.reward_service.exception.AuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    public String getAuth0UserIdFromToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new AuthenticationException("Invalid authentication token");
        }

        try {
            DecodedJWT jwt = JWT.decode(token.replace("Bearer ", ""));
            return jwt.getSubject();
        } catch (JWTDecodeException ex) {
            throw new AuthenticationException("Invalid authentication token");
        }
    }
}
package com.example.reward_service.utils;

import com.example.reward_service.exception.AuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthUtils {
    public static String getAuth0UserIdFromToken(String token) {
        if (token == null || token.trim().isEmpty()) {
        }

        try {
            DecodedJWT jwt = JWT.decode(token.replace("Bearer ", ""));
            return jwt.getSubject();
        } catch (JWTDecodeException ex) {
            throw new AuthenticationException("Invalid authentication token");
        }
    }
}
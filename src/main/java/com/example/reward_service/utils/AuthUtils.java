package com.example.reward_service.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthUtils {
    // Extracts the Auth0 UserID (the 'sub' claim) from the token.
    public static String getAuth0UserIdFromToken(String token) {
        try {
            // Remove "Bearer " prefix if present.
            String actualToken = token.replace("Bearer", "").trim();
            DecodedJWT decodedJWT = JWT.decode(actualToken);
            return decodedJWT.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token: " + e.getMessage());
        }
    }
}

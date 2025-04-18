package com.example.reward_service.utils;

import com.example.reward_service.exception.AuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthUtilsTest {

    // Valid token for testing (sample only, not a real token)
    private static final String VALID_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdXRoMHwxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final String EXPECTED_USER_ID = "auth0|1234567890";

    private AuthUtils authUtils;

    @BeforeEach
    void setUp() {
        authUtils = new AuthUtils();
    }

    @Test
    void testGetAuth0UserIdFromToken_ValidToken() {
        // Act
        String userId = authUtils.getAuth0UserIdFromToken(VALID_TOKEN);
        // Assert
        assertEquals(EXPECTED_USER_ID, userId);
    }

    @Test
    void testGetAuth0UserIdFromToken_ValidTokenWithoutBearer() {
        // Arrange: token without "Bearer " prefix
        String token = VALID_TOKEN.replace("Bearer ", "");
        // Act
        String userId = authUtils.getAuth0UserIdFromToken(token);
        // Assert
        assertEquals(EXPECTED_USER_ID, userId);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-token",
            "Bearer invalid.token.format",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" // Incomplete token
    })
    void testGetAuth0UserIdFromToken_InvalidTokenFormat(String invalidToken) {
        // Act & Assert: should throw an AuthenticationException for invalid tokens
        AuthenticationException exception = assertThrows(
                AuthenticationException.class,
                () -> authUtils.getAuth0UserIdFromToken(invalidToken));
        assertEquals("Invalid authentication token", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "  " })
    void testGetAuth0UserIdFromToken_EmptyOrNullToken(String emptyToken) {
        // Act & Assert: should throw an AuthenticationException for empty or null
        // tokens
        AuthenticationException exception = assertThrows(
                AuthenticationException.class,
                () -> authUtils.getAuth0UserIdFromToken(emptyToken));
        assertEquals("Invalid authentication token", exception.getMessage());
    }
}

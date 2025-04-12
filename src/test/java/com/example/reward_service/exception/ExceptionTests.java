// File: ExceptionTests.java
package com.example.reward_service.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionTests {

    @Test
    void testAuthenticationException() {
        String message = "Authentication failed";
        AuthenticationException exception = new AuthenticationException(message);
        // Verify the message and the fact it's a runtime exception.
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testDatabaseOperationException() {
        String message = "Database error occurred";
        Exception cause = new Exception("Underlying DB issue");
        DatabaseOperationException exception = new DatabaseOperationException(message, cause);
        // Verify the message, the cause and that it extends RuntimeException.
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testInsufficientPointsException() {
        String message = "Insufficient reward points";
        InsufficientPointsException exception = new InsufficientPointsException(message);
        // Verify the message and inheritance.
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testInvalidRequestException() {
        String message = "Invalid request parameters";
        InvalidRequestException exception = new InvalidRequestException(message);
        // Check message and that it is a runtime exception.
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testResourceNotFoundException() {
        String message = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);
        // Ensure the exception returns the expected message and is a runtime exception.
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }
}

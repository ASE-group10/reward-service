// File: GlobalExceptionHandlerTest.java
package com.example.reward_service.handler;

import com.example.reward_service.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleAuthenticationException() {
        // Arrange
        AuthenticationException ex = new AuthenticationException("Invalid token");

        // Act
        ResponseEntity<?> response = exceptionHandler.handleAuthenticationException(ex);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertResponseBody(response, "Unauthorized", "Invalid token");
    }

    @Test
    void testHandleResourceNotFoundException() {
        // Arrange
        ResourceNotFoundException ex = new ResourceNotFoundException("Reward not found");

        // Act
        ResponseEntity<?> response = exceptionHandler.handleResourceNotFound(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertResponseBody(response, "Not Found", "Reward not found");
    }

    @Test
    void testHandleDatabaseOperationException() {
        // Arrange
        DatabaseOperationException ex = new DatabaseOperationException("Failed to save reward",
                new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = exceptionHandler.handleDatabaseOperationException(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertResponseBody(response, "Database Error", "Failed to save reward");
    }

    @Test
    void testHandleInvalidRequestException() {
        // Arrange
        InvalidRequestException ex = new InvalidRequestException("Missing required fields");

        // Act
        ResponseEntity<?> response = exceptionHandler.handleInvalidRequests(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertResponseBody(response, "Bad Request", "Missing required fields");
    }

    @Test
    void testHandleInsufficientPointsException() {
        // Arrange
        InsufficientPointsException ex = new InsufficientPointsException("Not enough points to redeem");

        // Act
        ResponseEntity<?> response = exceptionHandler.handleInvalidRequests(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertResponseBody(response, "Bad Request", "Not enough points to redeem");
    }

    @Test
    void testHandleDataAccessException() {
        // Arrange
        DataAccessException ex = new DataIntegrityViolationException("Constraint violation");

        // Act
        ResponseEntity<?> response = exceptionHandler.handleDataAccessException(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertResponseBody(response, "Database Error", "Error accessing data");
    }

    @Test
    void testHandleGenericException() {
        // Arrange
        Exception ex = new RuntimeException("Unexpected error");

        // Act
        ResponseEntity<?> response = exceptionHandler.handleGenericException(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertResponseBody(response, "Internal Error", "An unexpected error occurred");
    }

    /**
     * Helper method to verify response body structure
     */
    private void assertResponseBody(ResponseEntity<?> response, String expectedError, String expectedMessage) {
        assertNotNull(response.getBody());
        // Access the properties using reflection
        Object errorResponse = response.getBody();
        assertEquals(expectedError, getFieldValue(errorResponse, "error"));
        assertEquals(expectedMessage, getFieldValue(errorResponse, "message"));
    }

    private String getFieldValue(Object object, String fieldName) {
        try {
            String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            return (String) object.getClass().getMethod(getterMethodName).invoke(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get field value: " + fieldName, e);
        }
    }
}

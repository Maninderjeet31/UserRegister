package com.project.userRegistration.exception;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setup() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void ExceptionThrown_whenUserRegistrationFailed() throws Exception {

        UserRegistrationFailedException e = new UserRegistrationFailedException("Test fail");
        ResponseEntity<RegistrationFailedMessage> response = globalExceptionHandler.registrationFailedHandler(e);

        assertEquals("400", 400, response.getStatusCode().value());
    }

    @Test
    public void ExceptionThrown_whenValidationFailHandler() throws Exception {

        BindingResult bindingResult = mock(BindingResult.class);
        List<FieldError> fieldErrors = List.of(
                new FieldError("userInput", "username", "Username is required"),
                new FieldError("userInput", "password", "Password is required"),
                new FieldError("userInput", "ipAddress", "IP Address is required")
        );

        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodParameter methodParameter = mock(MethodParameter.class); // dummy param
        MethodArgumentNotValidException e = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.validationFailHandler(e);

        Map<String, String> body = response.getBody();
        assertEquals("200", "Username is required", body.get("username"));
        assertEquals("200", "Password is required", body.get("password"));
    }
}
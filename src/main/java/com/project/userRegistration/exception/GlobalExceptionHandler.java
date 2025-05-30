package com.project.userRegistration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserRegistrationFailedException.class)
    public ResponseEntity<RegistrationFailedMessage> registrationFailedHandler(UserRegistrationFailedException e){

        RegistrationFailedMessage response = new RegistrationFailedMessage();

        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationFailHandler(MethodArgumentNotValidException e){

        Map<String, String> errorMap = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(er -> {
            errorMap.put(er.getField(), er.getDefaultMessage());
        });

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    /*
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RegistrationFailedMessage> constraintFailHandler(ConstraintViolationException e){

        Set<String> errorMap = new HashSet<>();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String message = violation.getMessage();
            errorMap.add(message);
        }

        RegistrationFailedMessage response = new RegistrationFailedMessage();

        response.setMessage(errorMap.stream().findFirst().get() + "\n");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
     */
}

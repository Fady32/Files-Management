package com.filemanagement.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Entity not found");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Data Integrity Violation");
        response.put("message", "A unique constraint was violated ");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid JSON");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();

        if (ex.getCause() instanceof EntityNotFoundException) {
            response.put("error", "Entity not found");
            response.put("message", ex.getCause().getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (ex.getCause() instanceof AccessDeniedException) {
            response.put("error", "Access Denied");
            response.put("message", ex.getCause().getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleEnumParseError(MethodArgumentTypeMismatchException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid Request");
        response.put("message", "Invalid value for enum: " + ex.getValue() + ". Accepted values are [EDIT, VIEW, DELETE].");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

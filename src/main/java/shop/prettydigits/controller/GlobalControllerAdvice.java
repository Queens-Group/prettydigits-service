package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 9:43 PM
@Last Modified 6/21/2024 9:43 PM
Version 1.0
*/

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.prettydigits.dto.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiResponse<Map<String, String>> response = new ApiResponse<>();
        response.setCode(400);
        response.setMessage("Invalid Arguments");
        response.setData(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> internalServerError(Exception ex) {
        log.error("[INTERNAL SERVER ERROR] {}", ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(500);
        response.setMessage(ex.getMessage());

        String causeClassName = Optional.ofNullable(ex.getCause())
                .map(Throwable::getClass)
                .map(Class::getCanonicalName)
                .orElse(null);
        response.setData(causeClassName);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<String>> accessDeniedHandler(AuthorizationDeniedException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(403);
        response.setMessage(ex.getMessage());
        response.setData("You are not allowed to access this resource. Please contact admin.");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse<String>> userDisabledHandler(AuthorizationDeniedException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(403);
        response.setMessage(ex.getMessage());
        response.setData("You are not allowed to access this resource. Please contact admin.");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}

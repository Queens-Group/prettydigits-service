package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 9:43 PM
@Last Modified 6/21/2024 9:43 PM
Version 1.0
*/

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
    public ResponseEntity<ApiResponse<String>> accessDeniedHandler(AuthorizationDeniedException ex, HttpServletRequest request) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(403);
        response.setMessage("You are not allowed to access this resource. Please contact admin.");
        response.setData(request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<String>> accessDeniedHandler(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(405);
        response.setMessage(ex.getMessage());
        response.setData(req.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> dataIntegrityViolationHandler(DataIntegrityViolationException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(400);
        response.setMessage(ex.getMostSpecificCause().getLocalizedMessage());
        response.setData(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<String>> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(400);
        response.setMessage(ex.getMessage());
        response.setData(ex.getParameterName());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}

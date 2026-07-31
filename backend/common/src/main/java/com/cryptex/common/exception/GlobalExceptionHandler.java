package com.cryptex.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Helper method for custom business exceptions
    // (Exceptions extending ApiException)
    private ResponseEntity<ErrorResponse> buildResponse(
            ApiException exception,
            HttpServletRequest request
    ){
        ErrorResponse response = new ErrorResponse(
                Instant.now(), // timestamp
                exception.getStatus().value(), // status
                exception.getStatus().getReasonPhrase(), // error
                exception.getMessage(), // message
                request.getRequestURI() // path
        );

        return ResponseEntity
                .status(exception.getStatus())
                .body(response);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            ApiException ex,
            HttpServletRequest request
    ){

        return buildResponse(ex, request);
    }


    // Helper method for Framework/library exceptions
    // (Spring Validation, Hibernate, etc)
    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ){

        ErrorResponse response = new ErrorResponse(
                Instant.now(), // timestamp
                status.value(), // status
                status.getReasonPhrase(), // error
                message, // message
                request.getRequestURI() // path
        );

        return ResponseEntity.status(status).body(response);
    }

    /*
    Annotated with @Valid
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){

        String message = ex.getBindingResult() // Multiple validation errors (BindingResult = FieldError1 + FieldError2, ...)
                .getFieldErrors() // [FieldError(firstName), FieldError(email),...]
                .stream()
                .findFirst() // FieldError(firstName)
                .map(DefaultMessageSourceResolvable::getDefaultMessage) // extracts -> "First name is required"
                .orElse("Validation failed"); // fallback

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request
        );
    }

    /*
    @Column(unique=true) private String email;
    2 users try abc@gmail.com -> 2nd insert reaches DB
    Duplicate key value violates unique constraint
    Hibernate wraps it as ConstraintViolationException.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ){

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Database constraint violation", // or ex.getSQLException()
                request
        );
    }

    /*
    Generic Exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request
    ){

        ex.printStackTrace();
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred.",
                request
        );
    }

}

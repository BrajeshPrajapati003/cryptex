package com.cryptex.auth.exception;

import com.cryptex.common.exception.ApiException;
import com.cryptex.common.exception.ErrorResponse;
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

    protected ResponseEntity<ErrorResponse> buildResponse(
            ApiException exception,
            HttpServletRequest request
    ){
        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                exception.getStatus().value(),
                exception.getStatus().getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation failed");

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                400,
                "Bad Request",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ){

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request
    ){

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                500,
                "Internal Server Error",
                "An unexpected error occurred.",
                request.getRequestURI()
        );

        ex.printStackTrace();

        return ResponseEntity.internalServerError().body(response);
    }
}

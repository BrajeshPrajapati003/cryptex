package com.cryptex.auth.exception;

import com.cryptex.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApiException {

    public InvalidCredentialsException() {
        super(HttpStatus.UNAUTHORIZED,
                "Invalid email or password.");
    }
}

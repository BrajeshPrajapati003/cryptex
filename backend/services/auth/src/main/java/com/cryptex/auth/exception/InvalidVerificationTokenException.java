package com.cryptex.auth.exception;

import com.cryptex.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidVerificationTokenException extends ApiException {
    public InvalidVerificationTokenException() {
        super(HttpStatus.UNAUTHORIZED,
                "Invalid or expired verification token.");
    }
}

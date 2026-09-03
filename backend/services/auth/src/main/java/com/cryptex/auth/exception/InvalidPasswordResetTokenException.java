package com.cryptex.auth.exception;

import com.cryptex.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidPasswordResetTokenException extends ApiException {
    public InvalidPasswordResetTokenException() {
        super(
                HttpStatus.UNAUTHORIZED,
                "Invalid or expired password reset token."
        );
    }
}

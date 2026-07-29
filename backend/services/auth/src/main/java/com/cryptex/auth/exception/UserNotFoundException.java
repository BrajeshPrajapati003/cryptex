package com.cryptex.auth.exception;

import com.cryptex.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException(String email) {
        super(HttpStatus.NOT_FOUND,
                "User not found with email: " + email);
    }
}

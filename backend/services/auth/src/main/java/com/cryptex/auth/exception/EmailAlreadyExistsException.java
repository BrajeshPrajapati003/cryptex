package com.cryptex.auth.exception;

import com.cryptex.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ApiException {

    public EmailAlreadyExistsException(String email){
        super(HttpStatus.CONFLICT, "Email already exists: " + email);
    }
}

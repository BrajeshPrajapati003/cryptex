package com.cryptex.notification.exception;

import com.cryptex.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class EmailSendingException extends ApiException {

    public EmailSendingException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public EmailSendingException(
            String message,
            Throwable cause
    ){
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                message,
                cause
        );
    }
}

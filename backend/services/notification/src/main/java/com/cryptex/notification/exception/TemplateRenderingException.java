package com.cryptex.notification.exception;

import com.cryptex.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class TemplateRenderingException extends ApiException {

    public TemplateRenderingException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public TemplateRenderingException(
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

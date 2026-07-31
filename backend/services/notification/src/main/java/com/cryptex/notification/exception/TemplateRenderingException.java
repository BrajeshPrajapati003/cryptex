package com.cryptex.notification.exception;

import com.cryptex.common.exception.ApiException;

public class TemplateRenderingException extends RuntimeException {
    public TemplateRenderingException(String message) {
        super(message);
    }
}

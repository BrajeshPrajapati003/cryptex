package com.cryptex.notification.exception;

import com.cryptex.common.exception.ApiException;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(String message) {
        super(message);
    }
}

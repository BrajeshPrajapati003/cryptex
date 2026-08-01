package com.cryptex.notification.exception;

import com.cryptex.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UnsupportedNotificationTypeException extends ApiException {

    public UnsupportedNotificationTypeException(String notificationType) {
        super(HttpStatus.BAD_REQUEST,
                "Unsupported notification type: " + notificationType
        );
    }
}

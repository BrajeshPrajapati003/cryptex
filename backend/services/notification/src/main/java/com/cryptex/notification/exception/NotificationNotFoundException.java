package com.cryptex.notification.exception;

import com.cryptex.common.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class NotificationNotFoundException extends ApiException {

    public NotificationNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND,
                "Notification not found with id: " + id
        );
    }
}

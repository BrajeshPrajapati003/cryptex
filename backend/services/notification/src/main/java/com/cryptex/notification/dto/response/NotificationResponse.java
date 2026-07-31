package com.cryptex.notification.dto.response;

import com.cryptex.notification.enums.NotificationStatus;

import java.time.Instant;
import java.util.UUID;

public record NotificationResponse(

        UUID id,
        NotificationStatus status,
        Instant createdAt
) {
}

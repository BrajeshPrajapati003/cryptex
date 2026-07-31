package com.cryptex.notification.dto.request;

import com.cryptex.notification.enums.NotificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record SendNotificationRequest(

        @NotBlank
        @Email
        String recipient,

        @NotNull
        NotificationType type,

        @NotNull
        Map<String, Object> variables
) {
}

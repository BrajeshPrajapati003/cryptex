package com.cryptex.notification.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    EMAIL_VERIFICATION(
            "Verify your email address",
            "verify-email"
    ),

    PASSWORD_RESET(
            "Reset your password",
            "reset-password"
    );

    private final String subject;
    private final String templateName;

}

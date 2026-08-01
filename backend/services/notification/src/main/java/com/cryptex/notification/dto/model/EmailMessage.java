package com.cryptex.notification.dto.model;

public record EmailMessage(

        String recipient,
        String subject,
        String body
) {
}

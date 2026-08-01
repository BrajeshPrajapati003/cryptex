package com.cryptex.notification.model;

public record EmailMessage(

        String recipient,
        String subject,
        String body
) {
}

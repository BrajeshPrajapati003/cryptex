package com.cryptex.auth.dto.request;

import java.util.Map;

public record SendNotificationRequest(

        String recipient,
        String type,
        Map<String, Object> variables
) {
}

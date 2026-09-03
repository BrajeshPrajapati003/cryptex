package com.cryptex.auth.client;

import com.cryptex.auth.dto.request.SendNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NotificationClient {

    private final RestClient notificationRestClient;

    public void sendNotification(SendNotificationRequest request){

        notificationRestClient
                .post()
                .uri("/api/v1/notifications")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}

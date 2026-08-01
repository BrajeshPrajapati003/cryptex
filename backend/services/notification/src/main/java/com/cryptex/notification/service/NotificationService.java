package com.cryptex.notification.service;

import com.cryptex.notification.dto.request.SendNotificationRequest;
import com.cryptex.notification.dto.response.NotificationResponse;

import java.util.UUID;

public interface NotificationService {

    NotificationResponse sendNotification(SendNotificationRequest request);

    NotificationResponse getNotification(UUID id);
}

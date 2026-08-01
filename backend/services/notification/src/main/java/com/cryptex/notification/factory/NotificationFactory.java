package com.cryptex.notification.factory;

import com.cryptex.notification.dto.request.SendNotificationRequest;
import com.cryptex.notification.entity.Notification;
import com.cryptex.notification.enums.NotificationChannel;
import com.cryptex.notification.enums.NotificationStatus;
import com.cryptex.notification.enums.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {

    public Notification create(
            SendNotificationRequest request,
            NotificationType type,
            String body
    ){

        return Notification.builder()
                .recipient(request.recipient())
                .subject(type.getSubject())
                .body(body)
                .type(type)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PENDING)
                .retryCount(0)
                .build();
    }
}

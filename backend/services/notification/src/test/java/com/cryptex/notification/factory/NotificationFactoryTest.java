package com.cryptex.notification.factory;

import com.cryptex.notification.dto.request.SendNotificationRequest;
import com.cryptex.notification.entity.Notification;
import com.cryptex.notification.enums.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationFactoryTest {

    private NotificationFactory notificationFactory;

    @BeforeEach
    void setUP(){
        notificationFactory = new NotificationFactory();
    }

    @Test
    void shouldCreateEmailVerificationNotification(){

        // Arrange
        SendNotificationRequest request =
                new SendNotificationRequest(
                        "brajesh@example.com",
                        NotificationType.EMAIL_VERIFICATION,
                        Map.of(
                                "name", "Brajesh",
                                "verificationLink", "http:localhost/verify"
                        )
                );

        String body = "<html>Email Verification</html>";

        // Act
        Notification notification =
                notificationFactory.create(
                        request,
                        NotificationType.EMAIL_VERIFICATION,
                        body
                );

        // Assert
        assertThat(notification).isNotNull();

        assertThat(notification.getRecipient())
                .isEqualTo("brajesh@example.com");


    }
}

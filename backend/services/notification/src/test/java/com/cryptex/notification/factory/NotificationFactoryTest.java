package com.cryptex.notification.factory;

import com.cryptex.notification.dto.request.SendNotificationRequest;
import com.cryptex.notification.entity.Notification;
import com.cryptex.notification.enums.NotificationChannel;
import com.cryptex.notification.enums.NotificationStatus;
import com.cryptex.notification.enums.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/*
State Testing
 */
public class NotificationFactoryTest {

    private NotificationFactory notificationFactory;
    private static final String RECIPIENT = "ex1@tester.com";

    @BeforeEach
    void setUp(){
        notificationFactory = new NotificationFactory();
    }

    @Test
    void shouldCreateEmailVerificationNotification(){

        // Arrange : CREATE INPUT
        SendNotificationRequest request =
                new SendNotificationRequest(
                        RECIPIENT,
                        NotificationType.EMAIL_VERIFICATION,
                        Map.of(
                                "name", "ex1 email",
                                "verificationLink", "http://localhost/verify"
                        )
                );

        String body = "<html>Email Verification</html>";

        // Act : CALL METHOD
        Notification notification = notificationFactory.create(
                request,
                NotificationType.EMAIL_VERIFICATION,
                body
        );


        // Assert : CHECK OUTPUT
        assertThat(notification).isNotNull();

        assertThat(notification.getRecipient())
                .isEqualTo(RECIPIENT);

        assertThat(notification.getSubject())
                .isEqualTo("Verify your email address");

        assertThat(notification.getBody())
                .isEqualTo(body);

        assertThat(notification.getType())
                .isEqualTo(NotificationType.EMAIL_VERIFICATION);

    }

    @Test
    void shouldCreatePasswordResetNotification(){

        // Arrange : CREATE INPUT
        SendNotificationRequest request =
                new SendNotificationRequest(
                        RECIPIENT,
                        NotificationType.PASSWORD_RESET,
                        Map.of(
                                "name", "ex1 password",
                                "resetLink", "http://localhost/reset"
                        )
                );

        String body = "<html>Reset your password</html>";

        // Act : CALL METHOD
        Notification notification = notificationFactory.create(
                request,
                NotificationType.PASSWORD_RESET,
                body
        );

        // Assert : CHECK OUTPUT
        assertThat(notification).isNotNull();

        assertThat(notification.getSubject())
                .isEqualTo("Reset your password");

        assertThat(notification.getType())
                .isEqualTo(NotificationType.PASSWORD_RESET);

    }

    @Test
    void shouldInitializeNewNotificationWithDefaultValues(){

        // Arrange : CREATE INPUT
        SendNotificationRequest request =
                new SendNotificationRequest(
                        RECIPIENT,
                        NotificationType.EMAIL_VERIFICATION,
                        // NotificationType is not the focus
                        // It's just an i/p required to create a valid Notification
                        // Hence, it can be anything for now
                        // (EMAIL_VERIFICATION or PASSWORD_RESET)

                        //
                        Map.of(
                                "name", "ex1@tester.com",
                                "verificationLink", "http://localhost/"
                        )
                );

        String body = "<html>Notification initialized as PENDING</html>";

        // Act : CALL METHOD
        Notification notification = notificationFactory.create(
                request,
                NotificationType.EMAIL_VERIFICATION,
                body
        );

        // Assert : CHECK OUTPUT
        assertThat(notification).isNotNull();

        assertThat(notification.getStatus())
                .isEqualTo(NotificationStatus.PENDING);

        assertThat(notification.getRetryCount())
                .isZero();

        assertThat(notification.getSentAt())
                .isNull();

        assertThat(notification.getFailureReason())
                .isNull();

    }

    @Test
    void shouldSetNotificationChannelToEmail(){

        // Arrange
        SendNotificationRequest request =
                new SendNotificationRequest(
                        RECIPIENT,
                        NotificationType.EMAIL_VERIFICATION,
                        Map.of(
                                "name", "ex1@tester.com",
                                "verificationLink", "http://localhost/"
                        )
                );

        String body = "<html>Notification Channel should be email</html>";

        // Act
        Notification notification =
                notificationFactory.create(
                        request,
                        NotificationType.EMAIL_VERIFICATION,
                        body
                );

        // Assert
        assertThat(notification).isNotNull();

        assertThat(notification.getChannel())
                .isEqualTo(NotificationChannel.EMAIL);
    }

}

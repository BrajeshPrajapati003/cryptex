package com.cryptex.notification.repository;

import com.cryptex.notification.entity.Notification;
import com.cryptex.notification.enums.NotificationChannel;
import com.cryptex.notification.enums.NotificationStatus;
import com.cryptex.notification.enums.NotificationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void shouldSaveAndFindNotification(){

        // Arrange
        Notification notification = Notification.builder()
                .recipient("ex1@tester.com")
                .subject("Verify your email")
                .body("<html>Email verification</html>")
                .type(NotificationType.EMAIL_VERIFICATION)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PENDING)
                .retryCount(0)
                .build();

        // Act
        Notification saved =
                notificationRepository.save(notification);

        Optional<Notification> result =
                notificationRepository.findById(saved.getId());

        // Assert
        assertThat(saved.getId())
                .isNotNull();

        assertThat(result)
                .isPresent();

        Notification found = result.get();

        assertThat(found.getRecipient())
                .isEqualTo("ex1@tester.com");

        assertThat(found.getSubject())
                .isEqualTo("Verify your email");

        assertThat(found.getBody())
                .isEqualTo("<html>Email verification</html>");

        assertThat(found.getType())
                .isEqualTo(NotificationType.EMAIL_VERIFICATION);

        assertThat(found.getChannel())
                .isEqualTo(NotificationChannel.EMAIL);

        assertThat(found.getStatus())
                .isEqualTo(NotificationStatus.PENDING);

        assertThat(found.getRetryCount())
                .isZero();
    }

    @Test
    void shouldPopulateAuditFieldsWhenNotificationIsSaved(){

        // Arrange
        Notification notification = Notification.builder()
                .recipient("ex1@tester.com")
                .subject("Audit Test")
                .body("<html>Audit test</html>")
                .type(NotificationType.EMAIL_VERIFICATION)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PENDING)
                .build();

        // Act
        Notification saved =
                notificationRepository.save(notification);

        // Assert
        assertThat(saved.getCreatedAt())
                .isNotNull();

        assertThat(saved.getUpdatedAt())
                .isNotNull();
    }
}

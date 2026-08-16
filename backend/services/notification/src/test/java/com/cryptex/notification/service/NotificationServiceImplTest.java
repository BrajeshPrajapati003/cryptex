package com.cryptex.notification.service;

import com.cryptex.notification.dto.request.SendNotificationRequest;
import com.cryptex.notification.dto.response.NotificationResponse;
import com.cryptex.notification.entity.Notification;
import com.cryptex.notification.enums.NotificationChannel;
import com.cryptex.notification.enums.NotificationStatus;
import com.cryptex.notification.enums.NotificationType;
import com.cryptex.notification.exception.EmailSendingException;
import com.cryptex.notification.exception.NotificationNotFoundException;
import com.cryptex.notification.factory.NotificationFactory;
import com.cryptex.notification.mapper.NotificationMapper;
import com.cryptex.notification.model.EmailMessage;
import com.cryptex.notification.repository.NotificationRepository;
import com.cryptex.notification.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {

    @Mock
    private NotificationRepository repository;

    @Mock
    private NotificationMapper mapper;

    @Mock
    private NotificationFactory factory;

    @Mock
    private TemplateService templateService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void shouldSendNotificationSuccessfully(){

        // Arrange
        SendNotificationRequest request =
                new SendNotificationRequest(
                        "ex1@tester.com",
                        NotificationType.EMAIL_VERIFICATION,
                        Map.of(
                                "name", "Brajesh",
                                "verificationLink", "http://localhost/verify"
                        )
                );

        String renderedBody =
                "<html>Email Verification</html>";


        Notification notification = Notification.builder()
                .recipient("ex1@tester.com")
                .subject("Verify your email address")
                .body(renderedBody)
                .type(NotificationType.EMAIL_VERIFICATION)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PENDING)
                .retryCount(0)
                .build();

        UUID notificationId = UUID.randomUUID();
        Instant createdAt = Instant.now();

        NotificationResponse response = new NotificationResponse(
                notificationId,
                NotificationStatus.SENT,
                createdAt
        );

        when(templateService.renderTemplate(
                request.type(),
                request.variables()
        )).thenReturn(renderedBody);

        when(factory.create(
                request,
                request.type(),
                renderedBody
        )).thenReturn(notification);

        when(mapper.toResponse(notification))
                .thenReturn(response);

        // Act
        NotificationResponse result =
                notificationService.sendNotification(request);

        // Assert
        assertThat(result)
                .isEqualTo(response);

        assertThat(notification.getStatus())
                .isEqualTo(NotificationStatus.SENT);

        assertThat(notification.getSentAt())
                .isNotNull();

        verify(templateService)
                .renderTemplate(request.type(), request.variables());

        verify(factory)
                .create(request, request.type(), renderedBody);

//        verify(emailService).sendEmail(any(EmailMessage.class));

        ArgumentCaptor<EmailMessage> emailMessageCaptor =
                ArgumentCaptor.forClass(EmailMessage.class);

        verify(emailService)
                .sendEmail(emailMessageCaptor.capture()); // Mockito captures the actual EmailMessage

        // Get the captured object
        /*
        The real object that your service passed to EmailService
         */
        EmailMessage capturedMessage =
                emailMessageCaptor.getValue();

        // Assert its contents
        assertThat(capturedMessage.recipient())
                .isEqualTo("ex1@tester.com");

        assertThat(capturedMessage.subject())
                .isEqualTo("Verify your email address");

        assertThat(capturedMessage.body())
                .isEqualTo(renderedBody);

        verify(repository)
                .save(notification);

        verify(mapper)
                .toResponse(notification);
    }

    @Test
    void shouldMarkNotificationAsFailedWhenEmailSendingFails(){

        // Arrange
        SendNotificationRequest request =
                new SendNotificationRequest(
                        "ex1@tester.com",
                        NotificationType.EMAIL_VERIFICATION,
                        Map.of(
                                "name", "Brajesh",
                                "verificationLink", "http://localhost/verify"
                        )
                );

        String body = "<html>Email Verification</html>";

        Notification notification = Notification.builder()
                .recipient("ex1@tester.com")
                .subject("Verify your email")
                .body(body)
                .type(NotificationType.EMAIL_VERIFICATION)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PENDING)
                .retryCount(0)
                .build();

        when(templateService.renderTemplate(
                request.type(),
                request.variables()
        )).thenReturn(body);

        when(factory.create(
                request,
                request.type(),
                body
        )).thenReturn(notification);

        doThrow(new EmailSendingException("SMTP failed"))
                .when(emailService)
                .sendEmail(any(EmailMessage.class));

        // Act & Assert
        assertThrows(
                EmailSendingException.class,
                ()-> notificationService.sendNotification(request)
        );

        // Assert
        assertThat(notification.getStatus())
                .isEqualTo(NotificationStatus.FAILED);

        verify(repository)
                .save(notification);

    }

    @Test
    void shouldReturnNotificationWhenNotificationExists(){

        // Arrange
        UUID id = UUID.randomUUID();

        Notification notification = Notification.builder()
                .recipient("ex1@tester.com")
                .subject("Verify your email address")
                .body("<html>Email Verification</html>")
                .type(NotificationType.EMAIL_VERIFICATION)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.SENT)
                .retryCount(0)
                .build();

        NotificationResponse response = new NotificationResponse(
                id,
                NotificationStatus.SENT,
                Instant.now()
        );

        when(repository.findById(id))
                .thenReturn(Optional.of(notification));

        when(mapper.toResponse(notification))
                .thenReturn(response);

        // Act
        NotificationResponse result =
                notificationService.getNotification(id);

        // Assert
        assertThat(result)
                .isEqualTo(response);

        verify(repository)
                .findById(id);

        verify(mapper)
                .toResponse(notification);
    }

    @Test
    void shouldThrowNotificationNotFoundExceptionWhenNotificationDoesNotExist(){

        // Arrange
        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                NotificationNotFoundException.class,
                ()-> notificationService.getNotification(id)
        );

        verify(repository)
                .findById(id);

        verify(mapper, never())
                .toResponse(any(Notification.class));
    }
}

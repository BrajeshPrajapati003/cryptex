package com.cryptex.notification.service.impl;

import com.cryptex.notification.dto.request.SendNotificationRequest;
import com.cryptex.notification.dto.response.NotificationResponse;
import com.cryptex.notification.entity.Notification;
import com.cryptex.notification.enums.NotificationType;
import com.cryptex.notification.exception.EmailSendingException;
import com.cryptex.notification.exception.NotificationNotFoundException;
import com.cryptex.notification.factory.NotificationFactory;
import com.cryptex.notification.mapper.NotificationMapper;
import com.cryptex.notification.model.EmailMessage;
import com.cryptex.notification.repository.NotificationRepository;
import com.cryptex.notification.service.EmailService;
import com.cryptex.notification.service.NotificationService;
import com.cryptex.notification.service.TemplateService;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    private final NotificationFactory notificationFactory;

    private final TemplateService templateService;
    private final EmailService emailService;

    private final Configuration configuration;

    @Override
    public NotificationResponse sendNotification(SendNotificationRequest request) {

        NotificationType type = request.type();

        String body = templateService.renderTemplate(
                type,
                request.variables()
        );

        Notification notification =
                notificationFactory.create(
                        request,
                        type,
                        body
                );

        try{

            EmailMessage message = new EmailMessage(
                    notification.getRecipient(),
                    notification.getSubject(),
                    notification.getBody()
            );

            emailService.sendEmail(message);

            notification.markSent();
        }catch (EmailSendingException ex){

            notification.markFailed("SMTP delivery failed.");

            /*
            Don't expose:
                Authentication failed
                TLS failed
                Socket timeout
            to the client.
            Those stay in the logs through the exception cause.
             */
            notificationRepository.save(notification);

            throw ex;
        }

        notificationRepository.save(notification);

        return notificationMapper.toResponse(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotification(UUID id) {

        Notification notification = notificationRepository
                        .findById(id)
                        .orElseThrow(()->
                                new NotificationNotFoundException(id));

        return notificationMapper.toResponse(notification);
    }
}

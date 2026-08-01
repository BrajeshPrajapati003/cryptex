package com.cryptex.notification.provider;

import com.cryptex.notification.config.NotificationProperties;
import com.cryptex.notification.model.EmailMessage;
import com.cryptex.notification.exception.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpEmailProvider implements EmailProvider{

    private final JavaMailSender mailSender;
    private final NotificationProperties properties;

    @Override
    public void send(EmailMessage message){

        try{

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(properties.getFrom());
            helper.setReplyTo(properties.getReplyTo());

            helper.setTo(message.recipient());
            helper.setSubject(message.subject());

            helper.setText(message.body(), true);

            mailSender.send(mimeMessage);
        }catch (MessagingException | MailException ex){

            throw new EmailSendingException(
                    "Failed to send email: " + ex.getMessage()
            );
        }
    }
}

package com.cryptex.notification.service;

import com.cryptex.notification.exception.EmailSendingException;
import com.cryptex.notification.model.EmailMessage;
import com.cryptex.notification.provider.EmailProvider;
import com.cryptex.notification.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

/*
Behavior Testing
 */
@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private EmailProvider emailProvider;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void shouldDelegateEmailSendingToProvider(){

        // Arrange
        EmailMessage message = new EmailMessage(
                "ex1@tester.com",
                "Email Delegation",
                "<html>Hello</html>"
        );
        // creates a message

        // Act
        emailService.sendEmail(message);
        // Inside the real service: emailProvider.send(message);
        // But emailProvider is a mock
        // Mockito records: "send(message) was called"

        // Assert
        verify(emailProvider).send(message); // static method in Mockito class
        // asks Mockito: "Did someone call send(message)"
        // yes: test passes; no: test fails

    }

    @Test
    void shouldPropagateExceptionWhenProviderFails(){

        // Arrange
        EmailMessage message = new EmailMessage(
                "ex1@tester.com",
                "Propagate Exception",
                "<html>Exception Propagation</html>"
        );

        doThrow(new EmailSendingException("SMTP failed"))
                .when(emailProvider)
                .send(message);

        // Act & Assert
        assertThrows(
                EmailSendingException.class,
                ()-> emailService.sendEmail(message)
        );
    }
}

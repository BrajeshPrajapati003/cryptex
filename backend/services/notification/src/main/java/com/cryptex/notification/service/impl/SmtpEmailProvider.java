package com.cryptex.notification.service.impl;

import com.cryptex.notification.dto.model.EmailMessage;
import com.cryptex.notification.service.EmailProvider;
import org.springframework.stereotype.Component;

@Component
public class SmtpEmailProvider implements EmailProvider{

    @Override
    public void send(EmailMessage message){

        throw new UnsupportedOperationException(
                "SMTP provider not implemented yet."
        );
    }
}

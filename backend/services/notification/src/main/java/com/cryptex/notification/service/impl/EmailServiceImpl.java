package com.cryptex.notification.service.impl;

import com.cryptex.notification.model.EmailMessage;
import com.cryptex.notification.provider.EmailProvider;
import com.cryptex.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailProvider emailProvider;

    @Override
    public void sendEmail(EmailMessage message) {

        emailProvider.send(message);
    }
}

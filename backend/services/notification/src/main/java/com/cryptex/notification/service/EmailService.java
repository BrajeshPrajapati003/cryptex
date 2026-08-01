package com.cryptex.notification.service;

import com.cryptex.notification.model.EmailMessage;

public interface EmailService {

    void sendEmail(EmailMessage message);
}

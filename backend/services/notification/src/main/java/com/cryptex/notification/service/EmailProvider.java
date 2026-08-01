package com.cryptex.notification.service;

import com.cryptex.notification.dto.model.EmailMessage;

public interface EmailProvider {

    void send(EmailMessage message);
}

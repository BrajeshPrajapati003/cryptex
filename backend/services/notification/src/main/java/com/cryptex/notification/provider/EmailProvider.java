package com.cryptex.notification.provider;

import com.cryptex.notification.model.EmailMessage;

public interface EmailProvider {

    void send(EmailMessage message);

    /*
    FUTURE SCOPE
     */
//    void sendWithAttachments();
//    void sendBulk();

}

package com.cryptex.notification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "notification.email")
public class NotificationProperties {

    private String from;
    private String replyTo;


    /*
    FUTURE SCOPE
     */
//    private Retry retry;
//    private Template template;
}

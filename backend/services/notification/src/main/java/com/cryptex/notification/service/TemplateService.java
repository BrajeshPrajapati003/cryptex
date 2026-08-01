package com.cryptex.notification.service;

import com.cryptex.notification.enums.NotificationType;

import java.util.Map;

public interface TemplateService {

    String renderTemplate(
            NotificationType type,
            Map<String, Object> variables
    );
}

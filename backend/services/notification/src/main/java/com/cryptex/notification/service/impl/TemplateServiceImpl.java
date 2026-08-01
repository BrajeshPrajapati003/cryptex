package com.cryptex.notification.service.impl;

import com.cryptex.notification.service.TemplateService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Override
    public String render(
            String template,
            Map<String, Object> variables
    ){
        throw new UnsupportedOperationException(
                "Template rendering not implemented yet."
        );
    }
}

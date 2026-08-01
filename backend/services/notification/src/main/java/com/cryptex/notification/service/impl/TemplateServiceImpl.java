package com.cryptex.notification.service.impl;

import com.cryptex.notification.enums.NotificationType;
import com.cryptex.notification.exception.TemplateRenderingException;
import com.cryptex.notification.service.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final Configuration configuration;

    @Override
    public String renderTemplate(
            NotificationType type,
            Map<String, Object> variables
    ){

        try {
            Template template =
                    configuration.getTemplate(type.getTemplateName() + ".ftlh");

            StringWriter writer = new StringWriter();

            template.process(variables, writer);

            return writer.toString();
        }catch (IOException | TemplateException ex){

            throw new TemplateRenderingException(
                    "Failed to render template: " + type.name(),
                    ex
            );
        }
    }
}

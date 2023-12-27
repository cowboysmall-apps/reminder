package com.cowboysmall.reminder.component;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

@Component
public class ReminderTemplateComponentImpl implements ReminderTemplateComponent {

    @Autowired
    private Configuration configuration;


    //_________________________________________________________________________

    @Override
    @Traceable(Level.DEBUG)
    public String processTemplate(String templateName, Map<String, String> model) {

        try {

            StringWriter writer = new StringWriter(1024);
            configuration.getTemplate(templateName).process(model, writer);
            return writer.toString();

        } catch (Exception e) {

            throw new ReminderTemplateComponentException(e);
        }
    }
}

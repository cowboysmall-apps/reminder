package com.cowboysmall.reminder.component;

import com.cowboysmall.reminder.TestConfiguration;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.Writer;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfiguration.class)
public class ReminderTemplateComponentTest {

    @Autowired
    private ReminderTemplateComponent reminderTemplateComponent;

    @MockBean
    private Configuration configuration;

    @MockBean
    private Template template;


    //_________________________________________________________________________

    @Test
    public void processTemplate() throws Exception {

        Map<String, String> model = Map.of("key", "value");

        when(configuration.getTemplate(eq("TEMPLATE"))).thenReturn(template);
        doNothing().when(template).process(eq(model), any(Writer.class));

        reminderTemplateComponent.processTemplate("TEMPLATE", model);

        verify(configuration, times(1)).getTemplate("TEMPLATE");
        verify(template, times(1)).process(eq(model), any(Writer.class));
    }
}

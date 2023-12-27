package com.cowboysmall.reminder.component;

import java.util.Map;

public interface ReminderTemplateComponent {

    String processTemplate(String templateName, Map<String, String> model);
}

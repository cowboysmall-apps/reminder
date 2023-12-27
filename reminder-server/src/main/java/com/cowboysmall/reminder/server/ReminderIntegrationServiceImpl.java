package com.cowboysmall.reminder.server;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import com.cowboysmall.reminder.component.ReminderTemplateComponent;
import com.cowboysmall.reminder.connector.ReminderEmailConnector;
import com.cowboysmall.reminder.domain.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReminderIntegrationServiceImpl implements ReminderIntegrationService {

    @Autowired
    private ReminderTemplateComponent reminderTemplateComponent;

    @Autowired
    private ReminderEmailConnector reminderEmailConnector;


    @Value("${reminder.host.name}")
    private String server;

    @Value("${reminder.email.from}")
    private String from;

    @Value("${reminder.email.subject.confirmation}")
    private String subjectConfirmation;

    @Value("${reminder.email.subject.reminder}")
    private String subjectReminder;


    //_________________________________________________________________________

    @Override
    @Traceable(Level.INFO)
    public Reminder sendConfirm(Reminder reminder) {

        try {

            Map<String, String> model = new HashMap<>();
            model.put("server", server);
            model.put("token", reminder.getToken());

            reminderEmailConnector.sendEmail(
                    reminder.getEmail(),
                    from,
                    subjectConfirmation,
                    reminderTemplateComponent.processTemplate("confirm.ftl", model)
            );

            return reminder;

        } catch (Exception e) {

            throw new ReminderIntegrationServiceException(e);
        }
    }

    @Override
    @Traceable(Level.INFO)
    public Reminder sendReminder(Reminder reminder) {

        try {

            Map<String, String> model = new HashMap<>();
            model.put("message", reminder.getMessage());
            model.put("token", reminder.getToken());

            reminderEmailConnector.sendEmail(
                    reminder.getEmail(),
                    from,
                    subjectReminder,
                    reminderTemplateComponent.processTemplate("reminder.ftl", model)
            );

            return reminder;

        } catch (Exception e) {

            throw new ReminderIntegrationServiceException(e);
        }
    }
}

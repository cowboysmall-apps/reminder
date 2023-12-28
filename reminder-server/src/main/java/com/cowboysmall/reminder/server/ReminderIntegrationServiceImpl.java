package com.cowboysmall.reminder.server;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import com.cowboysmall.reminder.component.ReminderTemplateComponent;
import com.cowboysmall.reminder.connector.ReminderEmailConnector;
import com.cowboysmall.reminder.domain.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReminderIntegrationServiceImpl implements ReminderIntegrationService {

    private static final String CONFIRM_TEMPLATE = "confirm.ftl";
    private static final String REMINDER_TEMPLATE = "reminder.ftl";

    private static final String SERVER_KEY = "server";
    private static final String TOKEN_KEY = "token";
    private static final String MESSAGE_KEY = "message";

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

            reminderEmailConnector.sendEmail(
                    reminder.getEmail(),
                    from,
                    subjectConfirmation,
                    reminderTemplateComponent.processTemplate(
                            CONFIRM_TEMPLATE,
                            Map.of(SERVER_KEY, server, TOKEN_KEY, reminder.getToken())
                    )
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

            reminderEmailConnector.sendEmail(
                    reminder.getEmail(),
                    from,
                    subjectReminder,
                    reminderTemplateComponent.processTemplate(
                            REMINDER_TEMPLATE,
                            Map.of(MESSAGE_KEY, reminder.getMessage())
                    )
            );

            return reminder;

        } catch (Exception e) {

            throw new ReminderIntegrationServiceException(e);
        }
    }
}

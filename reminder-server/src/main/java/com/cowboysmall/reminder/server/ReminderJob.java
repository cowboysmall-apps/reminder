package com.cowboysmall.reminder.server;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReminderJob {

    @Autowired
    private ReminderService reminderService;


    //_________________________________________________________________________

    @Scheduled(cron = "0 0/5 * * * *")
    @Traceable(Level.INFO)
    public void handleOneOffReminders() {

        try {

            reminderService.handleOneOffReminders();

        } catch (Exception e) {

            throw new ReminderJobException(e);
        }
    }

    @Scheduled(cron = "0 0/5 * * * *")
    @Traceable(Level.INFO)
    public void handleRecurringReminders() {

        try {

            reminderService.handleRecurringReminders();

        } catch (Exception e) {

            throw new ReminderJobException(e);
        }
    }
}

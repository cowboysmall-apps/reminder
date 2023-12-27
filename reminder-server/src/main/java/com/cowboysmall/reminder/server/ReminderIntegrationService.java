package com.cowboysmall.reminder.server;

import com.cowboysmall.reminder.domain.Reminder;

public interface ReminderIntegrationService {

    Reminder sendConfirm(Reminder reminder);

    Reminder sendReminder(Reminder reminder);
}

package com.cowboysmall.reminder.server;

import com.cowboysmall.reminder.domain.Reminder;

public interface ReminderService {

    Reminder findReminder(Long id);

    Reminder createReminder(Reminder reminder);

    void confirmReminder(String token);

    void cancelReminder(String token);

    void handleRecurringReminders();

    void handleOneOffReminders();
}

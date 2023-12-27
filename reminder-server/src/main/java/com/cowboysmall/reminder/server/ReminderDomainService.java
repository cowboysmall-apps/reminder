package com.cowboysmall.reminder.server;

import com.cowboysmall.reminder.domain.Reminder;

import java.util.List;

public interface ReminderDomainService {

    Reminder findById(Long id);

    Reminder findByEnabledFalseAndToken(String token);

    List<Reminder> findRecurringRemindersInNeighbourhood();

    List<Reminder> findOneOffRemindersInNeighbourhood();

    Reminder saveReminder(Reminder reminder);

    void deleteReminder(Reminder reminder);

    void deleteByToken(String token);
}

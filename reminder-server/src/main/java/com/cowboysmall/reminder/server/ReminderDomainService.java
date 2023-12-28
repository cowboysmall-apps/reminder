package com.cowboysmall.reminder.server;

import com.cowboysmall.reminder.domain.Reminder;
import org.springframework.data.util.Streamable;

public interface ReminderDomainService {

    Reminder findById(Long id);

    Reminder findByEnabledFalseAndToken(String token);

    Streamable<Reminder> findRecurringRemindersInNeighbourhood();

    Streamable<Reminder> findOneOffRemindersInNeighbourhood();

    Reminder saveReminder(Reminder reminder);

    void deleteReminder(Reminder reminder);

    void deleteByToken(String token);
}

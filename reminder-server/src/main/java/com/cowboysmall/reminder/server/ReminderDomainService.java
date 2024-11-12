package com.cowboysmall.reminder.server;

import com.cowboysmall.reminder.domain.Reminder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ReminderDomainService {

    Optional<Reminder> findById(Long id);

    Optional<Reminder> findByEnabledFalseAndToken(String token);

    List<Reminder> findRecurringRemindersInNeighbourhood(Instant instant);

    List<Reminder> findOneOffRemindersInNeighbourhood(Instant instant);

    Reminder saveReminder(Reminder reminder);

    void deleteReminder(Reminder reminder);

    void deleteByToken(String token);
}

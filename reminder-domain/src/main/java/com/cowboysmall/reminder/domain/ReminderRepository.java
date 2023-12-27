package com.cowboysmall.reminder.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findByEnabledTrueAndOneOffTrueAndDateAndTimeBetween(Date date, Date start, Date end);

    List<Reminder> findByEnabledTrueAndOneOffFalseAndTimeBetween(Date start, Date end);

    Optional<Reminder> findByEnabledFalseAndToken(String token);

    void deleteByToken(String token);
}

package com.cowboysmall.reminder.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import java.util.Date;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    Streamable<Reminder> findByEnabledTrueAndOneOffTrueAndDateAndTimeBetween(Date date, Date start, Date end);

    Streamable<Reminder> findByEnabledTrueAndOneOffFalseAndTimeBetween(Date start, Date end);

    Optional<Reminder> findByEnabledFalseAndToken(String token);

    void deleteByToken(String token);
}

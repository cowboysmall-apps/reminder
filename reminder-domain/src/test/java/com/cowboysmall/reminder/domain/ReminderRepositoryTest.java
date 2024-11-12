package com.cowboysmall.reminder.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
public class ReminderRepositoryTest {

    @Autowired
    private ReminderRepository reminderRepository;


    //_________________________________________________________________________

    @Test
    public void save() {

        Date date = Date.from(Instant.now());

        Reminder created = reminderRepository.save(createReminder(date, Boolean.FALSE, Boolean.FALSE, UUID.randomUUID().toString()));

        assertThat(created.getId(), is(notNullValue()));
    }

    @Test
    public void findByEnabledTrueAndOneOffTrueAndDateAndTimeBetween() {

        Instant now = Instant.now();

        Date date = Date.from(now);

        Date start = Date.from(now.minus(1, ChronoUnit.MINUTES));
        Date end = Date.from(now.plus(4, ChronoUnit.MINUTES));

        reminderRepository.save(createReminder(date, Boolean.TRUE, Boolean.TRUE, UUID.randomUUID().toString()));
        reminderRepository.save(createReminder(date, Boolean.TRUE, Boolean.FALSE, UUID.randomUUID().toString()));
        reminderRepository.save(createReminder(date, Boolean.FALSE, Boolean.TRUE, UUID.randomUUID().toString()));
        reminderRepository.save(createReminder(date, Boolean.FALSE, Boolean.FALSE, UUID.randomUUID().toString()));

        List<Reminder> found = reminderRepository.findByEnabledTrueAndOneOffTrueAndDateAndTimeBetween(date, start, end);

        assertThat(found.size(), is(1));
    }

    @Test
    public void findByEnabledTrueAndOneOffFalseAndTimeBetween() {

        Instant now = Instant.now();

        Date date = Date.from(now);

        Date start = Date.from(now.minus(1, ChronoUnit.MINUTES));
        Date end = Date.from(now.plus(4, ChronoUnit.MINUTES));

        reminderRepository.save(createReminder(date, Boolean.FALSE, Boolean.TRUE, UUID.randomUUID().toString()));
        reminderRepository.save(createReminder(date, Boolean.FALSE, Boolean.FALSE, UUID.randomUUID().toString()));
        reminderRepository.save(createReminder(date, Boolean.TRUE, Boolean.TRUE, UUID.randomUUID().toString()));
        reminderRepository.save(createReminder(date, Boolean.TRUE, Boolean.FALSE, UUID.randomUUID().toString()));

        List<Reminder> found = reminderRepository.findByEnabledTrueAndOneOffFalseAndTimeBetween(start, end);

        assertThat(found.size(), is(1));
    }

    @Test
    public void findByEnabledFalseAndToken() {

        Date date = Date.from(Instant.now());

        String token1 = UUID.randomUUID().toString();
        reminderRepository.save(createReminder(date, Boolean.FALSE, Boolean.TRUE, token1));

        String token2 = UUID.randomUUID().toString();
        reminderRepository.save(createReminder(date, Boolean.FALSE, Boolean.FALSE, token2));

        assertThat(reminderRepository.findByEnabledFalseAndToken(token1).isEmpty(), is(true));
        assertThat(reminderRepository.findByEnabledFalseAndToken(token2).isEmpty(), is(false));
    }


    //_________________________________________________________________________

    private Reminder createReminder(Date date, Boolean oneOff, Boolean enabled, String token) {

        return Reminder.builder()
                .email("jerry@cowboysmall.com")
                .message("Test message...")
                .oneOff(oneOff)
                .date(date)
                .time(date)
                .enabled(enabled)
                .token(token)
                .build();
    }
}

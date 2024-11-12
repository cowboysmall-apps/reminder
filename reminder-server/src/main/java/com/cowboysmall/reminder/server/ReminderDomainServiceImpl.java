package com.cowboysmall.reminder.server;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import com.cowboysmall.reminder.domain.Reminder;
import com.cowboysmall.reminder.domain.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderDomainServiceImpl implements ReminderDomainService {

    @Autowired
    private ReminderRepository reminderRepository;


    //_________________________________________________________________________

    @Override
    @Traceable(Level.INFO)
    public Optional<Reminder> findById(Long id) {

        return reminderRepository.findById(id);
    }

    @Override
    @Traceable(Level.INFO)
    public Optional<Reminder> findByEnabledFalseAndToken(String token) {

        return reminderRepository.findByEnabledFalseAndToken(token);
    }

    @Override
    @Traceable(Level.INFO)
    public List<Reminder> findRecurringRemindersInNeighbourhood(Instant instant) {

        return reminderRepository.findByEnabledTrueAndOneOffFalseAndTimeBetween(
                Date.from(instant.minus(1, ChronoUnit.MINUTES)),
                Date.from(instant.plus(4, ChronoUnit.MINUTES))
        );
    }

    @Override
    @Traceable(Level.INFO)
    public List<Reminder> findOneOffRemindersInNeighbourhood(Instant instant) {

        return reminderRepository.findByEnabledTrueAndOneOffTrueAndDateAndTimeBetween(
                Date.from(instant),
                Date.from(instant.minus(1, ChronoUnit.MINUTES)),
                Date.from(instant.plus(4, ChronoUnit.MINUTES))
        );
    }

    @Override
    @Traceable(Level.INFO)
    public Reminder saveReminder(Reminder reminder) {

        try {

            return reminderRepository.save(reminder);

        } catch (Exception e) {

            throw new ReminderDomainServiceException(e);
        }
    }

    @Override
    @Traceable(Level.INFO)
    public void deleteReminder(Reminder reminder) {

        try {

            reminderRepository.delete(reminder);

        } catch (Exception e) {

            throw new ReminderDomainServiceException(e);
        }
    }

    @Override
    @Traceable(Level.INFO)
    public void deleteByToken(String token) {

        try {

            reminderRepository.deleteByToken(token);

        } catch (Exception e) {

            throw new ReminderDomainServiceException(e);
        }
    }
}

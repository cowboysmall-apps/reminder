package com.cowboysmall.reminder.server;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import com.cowboysmall.reminder.domain.Reminder;
import com.cowboysmall.reminder.domain.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReminderDomainServiceImpl implements ReminderDomainService {

    @Autowired
    private ReminderRepository reminderRepository;


    //_________________________________________________________________________

    @Override
    @Traceable(Level.INFO)
    public Reminder findById(Long id) {

        return reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderDomainServiceException("not found"));
    }

    @Override
    @Traceable(Level.INFO)
    public Reminder findByEnabledFalseAndToken(String token) {

        return reminderRepository.findByEnabledFalseAndToken(token)
                .orElseThrow(() -> new ReminderDomainServiceException("not found"));
    }

    @Override
    @Traceable(Level.INFO)
    public Streamable<Reminder> findRecurringRemindersInNeighbourhood() {

        try {

            Instant now = Instant.now();

            return reminderRepository.findByEnabledTrueAndOneOffFalseAndTimeBetween(
                    Date.from(now.minus(1, ChronoUnit.MINUTES)),
                    Date.from(now.plus(4, ChronoUnit.MINUTES))
            );

        } catch (Exception e) {

            throw new ReminderDomainServiceException(e);
        }
    }

    @Override
    @Traceable(Level.INFO)
    public Streamable<Reminder> findOneOffRemindersInNeighbourhood() {

        try {

            Instant now = Instant.now();

            return reminderRepository.findByEnabledTrueAndOneOffTrueAndDateAndTimeBetween(
                    Date.from(now),
                    Date.from(now.minus(1, ChronoUnit.MINUTES)),
                    Date.from(now.plus(4, ChronoUnit.MINUTES))
            );

        } catch (Exception e) {

            throw new ReminderDomainServiceException(e);
        }
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

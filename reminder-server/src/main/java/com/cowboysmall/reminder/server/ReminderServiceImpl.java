package com.cowboysmall.reminder.server;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import com.cowboysmall.reminder.domain.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ReminderDomainService reminderDomainService;

    @Autowired
    private ReminderIntegrationService reminderIntegrationService;


    //_________________________________________________________________________

    @Override
    @Traceable(Level.INFO)
    public Reminder findReminder(Long id) {

        return reminderDomainService.findById(id)
                .orElseThrow(() -> new ReminderServiceException("reminder not found"));
    }

    @Override
    @Traceable(Level.INFO)
    public Reminder createReminder(Reminder reminder) {

        try {

            Reminder prepared = reminder.prepare();
            Reminder confirmed = reminderIntegrationService.sendConfirm(prepared);
            return reminderDomainService.saveReminder(confirmed);

        } catch (Exception e) {

            throw new ReminderServiceException(e);
        }
    }

    @Override
    @Traceable(Level.INFO)
    public void confirmReminder(String token) {

        reminderDomainService.findByEnabledFalseAndToken(token)
                .map(Reminder::confirm)
                .map(reminderDomainService::saveReminder)
                .orElseThrow(() -> new ReminderServiceException("reminder not found"));
    }

    @Override
    @Traceable(Level.INFO)
    public void cancelReminder(String token) {

        try {

            reminderDomainService.deleteByToken(token);

        } catch (Exception e) {

            throw new ReminderServiceException(e);
        }
    }

    @Override
    @Traceable(Level.INFO)
    public void handleRecurringReminders() {

        try {

            reminderDomainService.findRecurringRemindersInNeighbourhood(Instant.now())
                    .forEach(reminderIntegrationService::sendReminder);

        } catch (Exception e) {

            throw new ReminderServiceException(e);
        }
    }

    @Override
    @Traceable(Level.INFO)
    public void handleOneOffReminders() {

        try {

            reminderDomainService.findOneOffRemindersInNeighbourhood(Instant.now()).stream()
                    .map(reminderIntegrationService::sendReminder)
                    .forEach(reminderDomainService::deleteReminder);

        } catch (Exception e) {

            throw new ReminderServiceException(e);
        }
    }
}

package com.cowboysmall.reminder.server;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import com.cowboysmall.reminder.domain.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        try {

            return reminderDomainService.findById(id);

        } catch (Exception e) {

            throw new ReminderServiceException(e);
        }
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

        try {

            Reminder found = reminderDomainService.findByEnabledFalseAndToken(token);
            reminderDomainService.saveReminder(found.confirm());

        } catch (Exception e) {

            throw new ReminderServiceException(e);
        }
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

            reminderDomainService.findRecurringRemindersInNeighbourhood()
                    .forEach(reminderIntegrationService::sendReminder);

        } catch (Exception e) {

            throw new ReminderServiceException(e);
        }
    }

    @Override
    @Traceable(Level.INFO)
    public void handleOneOffReminders() {

        try {

            reminderDomainService.findOneOffRemindersInNeighbourhood()
                    .map(reminderIntegrationService::sendReminder)
                    .forEach(reminderDomainService::deleteReminder);

        } catch (Exception e) {

            throw new ReminderServiceException(e);
        }
    }
}

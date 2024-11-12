package com.cowboysmall.reminder.server;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import com.cowboysmall.reminder.domain.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reminder")
public class ReminderApiController {

    @Autowired
    private ReminderService reminderService;


    //_________________________________________________________________________

    @GetMapping("/{id}")
    @Traceable(Level.INFO)
    public Reminder getReminder(@PathVariable Long id) {

        return reminderService.findReminder(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Traceable(Level.INFO)
    public Reminder createReminder(@RequestBody Reminder reminder) {

        return reminderService.createReminder(reminder);
    }
}

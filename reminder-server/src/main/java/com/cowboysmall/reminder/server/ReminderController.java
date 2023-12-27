package com.cowboysmall.reminder.server;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reminder")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;


    //_________________________________________________________________________

    @GetMapping("/confirm/{token}")
    @Traceable(Level.INFO)
    public String confirmReminder(@PathVariable String token) {

        reminderService.confirmReminder(token);
        return "redirect:/confirm.html";
    }

    @GetMapping("/cancel/{token}")
    @Traceable(Level.INFO)
    public String cancelReminder(@PathVariable String token) {

        reminderService.cancelReminder(token);
        return "redirect:/cancel.html";
    }
}

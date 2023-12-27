package com.cowboysmall.reminder.connector;

public interface ReminderEmailConnector {

    void sendEmail(String to, String from, String subject, String message);
}

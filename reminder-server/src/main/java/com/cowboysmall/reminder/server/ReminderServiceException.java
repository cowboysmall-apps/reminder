package com.cowboysmall.reminder.server;

public class ReminderServiceException extends RuntimeException {

    public ReminderServiceException(String message) {

        super(message);
    }

    public ReminderServiceException(Throwable cause) {

        super(cause);
    }
}

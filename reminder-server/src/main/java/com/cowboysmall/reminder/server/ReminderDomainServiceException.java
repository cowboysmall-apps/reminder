package com.cowboysmall.reminder.server;

public class ReminderDomainServiceException extends RuntimeException {

    public ReminderDomainServiceException(String message) {

        super(message);
    }

    public ReminderDomainServiceException(Throwable cause) {

        super(cause);
    }
}

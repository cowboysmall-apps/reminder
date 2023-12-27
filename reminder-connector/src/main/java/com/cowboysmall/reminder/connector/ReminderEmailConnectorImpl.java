package com.cowboysmall.reminder.connector;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class ReminderEmailConnectorImpl implements ReminderEmailConnector {

    @Autowired
    private JavaMailSender mailSender;


    //_________________________________________________________________________

    @Override
    @Traceable(Level.DEBUG)
    public void sendEmail(String to, String from, String subject, String message) {

        try {

            MimeMessage mailMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(message, true);

            mailSender.send(mailMessage);

        } catch (Exception e) {

            throw new ReminderEmailConnectorException(e);
        }
    }
}

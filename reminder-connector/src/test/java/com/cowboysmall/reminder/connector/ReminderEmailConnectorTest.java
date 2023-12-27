package com.cowboysmall.reminder.connector;

import com.cowboysmall.reminder.TestConfiguration;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfiguration.class)
public class ReminderEmailConnectorTest {

    @Autowired
    private ReminderEmailConnector reminderEmailConnector;

    @MockBean
    private JavaMailSender mailSender;


    //_________________________________________________________________________

    @Test
    public void sendEmail() throws Exception {

        MimeMessage mimeMessage = new MimeMessage((Session) null);

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        reminderEmailConnector.sendEmail(
                "you@domain.com",
                "me@domain.com",
                "subject",
                "message"
        );

        assertThat(mimeMessage.getAllRecipients().length, is(1));
        assertThat(mimeMessage.getAllRecipients()[0].toString(), is("you@domain.com"));

        assertThat(mimeMessage.getFrom().length, is(1));
        assertThat(mimeMessage.getFrom()[0].toString(), is("me@domain.com"));

        assertThat(mimeMessage.getSubject(), is("subject"));
        assertThat(mimeMessage.getContent(), is("message"));
    }
}

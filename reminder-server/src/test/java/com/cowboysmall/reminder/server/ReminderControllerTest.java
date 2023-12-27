package com.cowboysmall.reminder.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReminderController.class)
public class ReminderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReminderService reminderService;


    //_________________________________________________________________________

    @Test
    public void confirmReminder() throws Exception {

        doNothing().when(reminderService).confirmReminder("TOKEN");

        mockMvc.perform(get("/reminder/confirm/TOKEN"))
                .andExpect(redirectedUrl("/confirm.html"))
                .andExpect(status().isFound());

        verify(reminderService, times(1)).confirmReminder("TOKEN");
    }

    @Test
    public void cancelReminder() throws Exception {

        doNothing().when(reminderService).cancelReminder("TOKEN");

        mockMvc.perform(get("/reminder/cancel/TOKEN"))
                .andExpect(redirectedUrl("/cancel.html"))
                .andExpect(status().isFound());

        verify(reminderService, times(1)).cancelReminder("TOKEN");
    }
}

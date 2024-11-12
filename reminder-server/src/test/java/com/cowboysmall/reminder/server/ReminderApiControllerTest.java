package com.cowboysmall.reminder.server;

import com.cowboysmall.reminder.domain.Reminder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReminderApiController.class)
public class ReminderApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReminderService reminderService;

    @Autowired
    private ObjectMapper objectMapper;


    //_________________________________________________________________________

    @Test
    public void getReminder() throws Exception {

        when(reminderService.findReminder(1L))
                .thenReturn(Reminder.builder().id(1L).build());

        mockMvc.perform(
                        get("/api/reminder/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(reminderService, times(1)).findReminder(1L);
    }

    @Test
    public void createReminder() throws Exception {

        Reminder reminder =
                Reminder.builder()
                        .id(1L)
                        .email("test@domain.com")
                        .message("message body")
                        .oneOff(true)
                        .dateTime("2021-01-01T12:30:00.000Z")
                        .build();

        when(reminderService.createReminder(any(Reminder.class))).then(returnsFirstArg());

        mockMvc.perform(
                        post("/api/reminder")
                                .content(objectMapper.writeValueAsString(reminder))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("test@domain.com"))
                .andExpect(jsonPath("$.message").value("message body"))
                .andExpect(jsonPath("$.oneOff").value(true))
                .andExpect(jsonPath("$.enabled").value(false));

        verify(reminderService, times(1)).createReminder(any(Reminder.class));
    }
}

package com.cowboysmall.reminder.server;

import com.cowboysmall.insight.Level;
import com.cowboysmall.insight.Traceable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ReminderControllerAdvice {

    @ResponseBody
    @ExceptionHandler({ReminderDomainServiceException.class, ReminderIntegrationServiceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Traceable(Level.ERROR)
    public Map<String, String> handleException(HttpServletRequest httpServletRequest, Exception exception) {

        Map<String, String> map = new HashMap<>();
        map.put("url", httpServletRequest.getRequestURI());
        map.put("error", exception.getMessage());
        return map;
    }
}

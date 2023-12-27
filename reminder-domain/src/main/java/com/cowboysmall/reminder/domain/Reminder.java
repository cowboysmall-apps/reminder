package com.cowboysmall.reminder.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "REMINDER")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Reminder {

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = "reminder_seq", sequenceName = "reminder_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reminder_seq")
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "MESSAGE", nullable = false, length = 1024)
    private String message;

    @Transient
    private String dateTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    @Temporal(TemporalType.TIME)
    @Column(name = "TIME", nullable = false)
    private Date time;

    @Column(name = "TOKEN", nullable = false)
    private String token;

    @Column(name = "ONE_OFF", nullable = false)
    private boolean oneOff;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;


    //_________________________________________________________________________

    public Reminder prepare() {

        Date date = Date.from(ZonedDateTime.parse(dateTime).toInstant());
        setEnabled(Boolean.FALSE);
        setToken(UUID.randomUUID().toString());
        setDate(date);
        setTime(date);
        return this;
    }

    public Reminder confirm() {

        setEnabled(Boolean.TRUE);
        return this;
    }
}

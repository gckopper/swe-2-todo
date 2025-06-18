package com.example.todo.domain.model;

import com.example.todo.domain.repository.CalendarSink;
import com.example.todo.domain.repository.CalendarSinkStub;
import com.example.todo.domain.service.CalendarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CalendarServiceImplTest {
    CalendarSink calendarSink;

    CalendarService calendarService;

    @BeforeEach
    void setUp() {
        calendarSink = new CalendarSinkStub();
        calendarService = new CalendarServiceImpl(calendarSink);
    }

    @Test
    void insertCompletionDateEventWithNotAssignedTask() {
        Task task = Task.builder()
                        .assignedToUser(null)
                        .build();

        Assertions.assertNull(calendarService.insertCompletionDateEvent(task));
    }
    @Test
    void insertCompletionDateEventWithAssignedUserWithoutToken() {
        User user = User.builder()
                .externalCalendarServiceToken(null)
                .build();
        Task task = Task.builder()
                .assignedToUser(user)
                .build();

        Assertions.assertNull(calendarService.insertCompletionDateEvent(task));
    }
    @Test
    void insertCompletionDateEventWithAssignedUserWithoutCompletionDate() {
        User user = User.builder()
                .externalCalendarServiceToken("MyToken")
                .build();
        Task task = Task.builder()
                .assignedToUser(user)
                .expectedCompletionDate(null)
                .build();

        Assertions.assertNull(calendarService.insertCompletionDateEvent(task));
    }
    @Test
    void insertCompletionDateEventWithAssignedUser() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        User user = User.builder()
                .externalCalendarServiceToken("MyToken")
                .build();
        Task task = Task.builder()
                .assignedToUser(user)
                .owner(user)
                .title("")
                .description("")
                .expectedCompletionDate(offsetDateTime)
                .build();

        CalendarEvent expected = CalendarEvent.builder()
                .title("Deadline: ")
                .description("")
                .startTime(offsetDateTime)
                .endTime(offsetDateTime.plusHours(1))
                .externalToken("MyToken")
                .build();
        Assertions.assertTrue(expected.equals(calendarService.insertCompletionDateEvent(task)));
    }

    @Test
    void editCompletionDateEventNotUsingCalendarService() {
        Task task = Task.builder()
                .title("")
                .description("")
                .build();
        Assertions.assertNull(calendarService.editCompletionDateEvent(task));
    }
    @Test
    void editCompletionDateEventWithoutCompletionDate() {
        User user = User.builder()
                .externalCalendarServiceToken("MyToken")
                .build();
        Task task = Task.builder()
                .assignedToUser(user)
                .owner(user)
                .title("")
                .description("")
                .build();

        Assertions.assertNull(calendarService.editCompletionDateEvent(task));
    }

    @Test
    void deleteCompletionDateEvent() {
    }
}
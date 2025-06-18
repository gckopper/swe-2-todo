package com.example.todo.domain.repository;

import com.example.todo.domain.model.CalendarEvent;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarSinkStub implements CalendarSink{

    @Override
    public CalendarEvent insertEvent(CalendarEvent event) {
        return event;
    }

    @Override
    public CalendarEvent editEvent(CalendarEvent event) {
        return event;
    }

    @Override
    public void deleteEvent(CalendarEvent event) {
    }
}
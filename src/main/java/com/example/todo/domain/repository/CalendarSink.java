package com.example.todo.domain.repository;

import com.example.todo.domain.model.CalendarEvent;

import jakarta.validation.constraints.NotNull;

public interface CalendarSink {
    @NotNull
    CalendarEvent insertEvent(@NotNull CalendarEvent event);
    
    @NotNull
    CalendarEvent editEvent(@NotNull CalendarEvent event);
    
    void deleteEvent(@NotNull CalendarEvent event);
} 
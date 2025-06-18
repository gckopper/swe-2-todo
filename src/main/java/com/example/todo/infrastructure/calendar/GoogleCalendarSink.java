package com.example.todo.infrastructure.calendar;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Component;

import com.example.todo.domain.model.CalendarEvent;
import com.example.todo.domain.repository.CalendarSink;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleCalendarSink implements CalendarSink {
    private final GoogleCalendarService googleCalendarService;
    
    @Override
    public CalendarEvent insertEvent(CalendarEvent event) {
        try {
            Event googleEvent = createGoogleEvent(event);
            Event createdEvent = googleCalendarService.insertEvent(googleEvent, event.getExternalToken());
            
            event.setExternalEventId(createdEvent.getId());
            return event;
            
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to create calendar event", e);
        }
    }
    
    @Override
    public void editEvent(CalendarEvent event) {
        if (event.getExternalEventId() == null) {
            throw new IllegalArgumentException("Event must have an external ID to be edited");
        }

        try {
            Event googleEvent = createGoogleEvent(event);
            googleCalendarService.updateEvent(googleEvent, event.getExternalEventId(), event.getExternalToken());
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to update calendar event", e);
        }
    }
    
    @Override
    public void deleteEvent(CalendarEvent calendarEvent) {
        if (calendarEvent.getExternalEventId() == null) {
            throw new IllegalArgumentException("Event must have an external ID to be deleted");
        }

        try {
            googleCalendarService.deleteEvent(calendarEvent.getExternalEventId(), calendarEvent.getExternalToken());
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to delete calendar event", e);
        }
    }
    
    private Event createGoogleEvent(CalendarEvent event) {
        DateTime startDateTime = new DateTime(event.getStartTime().toInstant().toEpochMilli());
        DateTime endDateTime = new DateTime(event.getEndTime().toInstant().toEpochMilli());
        
        return googleCalendarService.createGoogleEvent(
            event.getTitle(),
            event.getDescription(),
            startDateTime,
            endDateTime
        );
    }
} 
package com.example.todo.domain.service;

import org.springframework.stereotype.Service;

import com.example.todo.domain.model.CalendarEvent;
import com.example.todo.domain.model.Task;
import com.example.todo.domain.repository.CalendarSink;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarSink calendarSink;
    
    public CalendarEvent insertCompletionDateEvent(Task task) {
        if (!shouldUseCalendarService(task)) {
            return null;
        }

        if (task.getExpectedCompletionDate() == null) {
            return null;
        }
        
        CalendarEvent calendarEvent = task.toCompletionDateCalendarEvent();

        return calendarSink.insertEvent(calendarEvent);
    }
    
    public void editCompletionDateEvent(Task task) {
        if (!shouldUseCalendarService(task)) {
            return;
        }

        if (task.getExpectedCompletionDate() == null) {
            deleteCompletionDateEvent(task);
            return;
        }

        CalendarEvent calendarEvent = task.toCompletionDateCalendarEvent();

        if (calendarEvent.getExternalEventId() == null)
        {
            calendarSink.insertEvent(calendarEvent);
            return;
        }

        calendarSink.editEvent(calendarEvent);
    }
    
    public void deleteCompletionDateEvent(Task task) {
        if (!shouldUseCalendarService(task)) {
            return;
        }

        CalendarEvent calendarEvent = task.toCompletionDateCalendarEvent();

        if (calendarEvent.getExternalEventId() == null)
        {
            return;
        }

        calendarSink.deleteEvent(calendarEvent);
    }

    private boolean shouldUseCalendarService(Task task) {
        return task.isAssigned() && 
            task.getAssignedToUser().usesExternalCalendarService();
    }
} 
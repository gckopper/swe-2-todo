package com.example.todo.domain.service;

import com.example.todo.domain.model.CalendarEvent;
import com.example.todo.domain.model.Task;

public interface CalendarService {
    CalendarEvent insertCompletionDateEvent(Task task);

    void editCompletionDateEvent(Task task);

    void deleteCompletionDateEvent(Task task);
}

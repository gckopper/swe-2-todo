package com.example.todo.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
public class Task {
    private UUID id;
    private User owner;
    private User assignedToUser;
    private String title;
    private String description;
    private TaskStatus status;
    private OffsetDateTime expectedCompletionDate;
    private String externalCalendarEventId;

    public CalendarEvent toCompletionDateCalendarEvent() {
        return CalendarEvent.builder()
            .externalEventId(externalCalendarEventId)
            .title("Deadline: " + title)
            .description(description)
            .startTime(expectedCompletionDate)
            .endTime(expectedCompletionDate.plusHours(1))
            .externalToken(owner.getExternalCalendarServiceToken())
            .build();
    }

    public boolean isAssigned() {
        return assignedToUser != null;
    }
}

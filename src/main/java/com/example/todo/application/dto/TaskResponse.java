package com.example.todo.application.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.*;

import com.example.todo.domain.model.TaskStatus;

@Data
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private UUID ownerUserId;
    private UUID assignedToUserId;
    private OffsetDateTime expectedCompletionDate;
    private String externalCalendarEventId;
} 
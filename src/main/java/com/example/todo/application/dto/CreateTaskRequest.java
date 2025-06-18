package com.example.todo.application.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Data;

import com.example.todo.domain.model.TaskStatus;

@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private UUID ownerId;
    private UUID assignedUserId;
    private OffsetDateTime expectedCompletionDate;
} 
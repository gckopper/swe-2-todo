package com.example.todo.application.mapper;

import java.util.List;
import java.util.UUID;

import com.example.todo.application.dto.CreateTaskRequest;
import com.example.todo.application.dto.TaskResponse;
import com.example.todo.application.dto.UpdateTaskRequest;
import com.example.todo.domain.model.Task;
import com.example.todo.domain.model.User;

public class TaskApiMapper {
    
    public static Task toDomain(CreateTaskRequest request) {
        return Task.builder()
            .id(UUID.randomUUID())
            .title(request.getTitle())
            .description(request.getDescription())
            .status(request.getStatus())
            .owner(User.builder().id(request.getOwnerId()).build())
            .assignedToUser(request.getAssignedUserId() != null ? 
                User.builder().id(request.getAssignedUserId()).build() : null)
            .expectedCompletionDate(request.getExpectedCompletionDate())
            .build();
    }

    public static Task toDomain(UUID id, UpdateTaskRequest request) {
        return Task.builder()
            .id(id)
            .title(request.getTitle())
            .description(request.getDescription())
            .status(request.getStatus())
            .assignedToUser(request.getAssignedUserId() != null ? 
                User.builder().id(request.getAssignedUserId()).build() : null)
            .expectedCompletionDate(request.getExpectedCompletionDate())
            .build();
    }

    public static void updateDomainFromRequest(Task task, UpdateTaskRequest request) {
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getExpectedCompletionDate() != null) {
            task.setExpectedCompletionDate(request.getExpectedCompletionDate());
        }
        if (request.getAssignedUserId() != null) {
            task.setAssignedToUser(User.builder().id(request.getAssignedUserId()).build());
        }
    }
    
    public static TaskResponse toResponse(Task task) {
        TaskResponse response = TaskResponse.builder().build();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setExpectedCompletionDate(task.getExpectedCompletionDate());
        response.setExternalCalendarEventId(task.getExternalCalendarEventId());
        
        if (task.getOwner() != null) {
            response.setOwnerUserId(task.getOwner().getId());
        }
        
        if (task.getAssignedToUser() != null) {
            response.setAssignedToUserId(task.getAssignedToUser().getId());;
        }
        
        return response;
    }
    
    public static List<TaskResponse> toResponseList(List<Task> tasks) {
        return tasks.stream()
            .map(TaskApiMapper::toResponse)
            .toList();
    }
} 
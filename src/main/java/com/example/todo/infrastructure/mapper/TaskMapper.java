package com.example.todo.infrastructure.mapper;

import com.example.todo.domain.model.Task;
import com.example.todo.infrastructure.dto.TaskDto;

public class TaskMapper {
    public static Task toDomain(TaskDto taskDto) {
        return Task.builder()
            .id(taskDto.getId())
            .title(taskDto.getTitle())
            .description(taskDto.getDescription())
            .status(taskDto.getStatus())
            .assignedToUser(UserMapper.toDomain(taskDto.getAssignedUser()))
            .owner(UserMapper.toDomain(taskDto.getOwnerUser()))
            .expectedCompletionDate(taskDto.getExpectedCompletionDate())
            .externalCalendarEventId(taskDto.getExternalCalendarEventId())
            .build();
    }

    public static TaskDto toDto(Task task) {
        return TaskDto.builder()
            .id(task.getId())
            .ownerUser(UserMapper.toDto(task.getOwner()))
            .assignedUser(UserMapper.toDto(task.getAssignedToUser()))
            .title(task.getTitle())
            .description(task.getDescription())
            .status(task.getStatus())
            .expectedCompletionDate(task.getExpectedCompletionDate())
            .externalCalendarEventId(task.getExternalCalendarEventId())
            .build();
    }
}

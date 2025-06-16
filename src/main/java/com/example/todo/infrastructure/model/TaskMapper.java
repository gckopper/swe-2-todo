package com.example.todo.infrastructure.model;

import com.example.todo.domain.model.Task;

public class TaskMapper {
    public static Task toDomain(TaskDto taskDto) {
        return Task.builder()
            .id(taskDto.getId())
            .title(taskDto.getTitle())
            .description(taskDto.getDescription())
            .status(taskDto.getStatus())
            .assignedToUser(UserMapper.toDomain(taskDto.getAssignedUser()))
            .owner(UserMapper.toDomain(taskDto.getOwnerUser()))
            .build();
    }

    public TaskDto toDto(Task task) {
        return TaskDto.builder()
            .id(task.getId())
            .ownerUser(UserMapper.toDto(task.getOwner()))
            .assignedUser(UserMapper.toDto(task.getAssignedToUser()))
            .title(task.getTitle())
            .description(task.getDescription())
            .status(task.getStatus())
            .build();
    }
}

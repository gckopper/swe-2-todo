package com.example.todo.infrastructure.model;

import com.example.todo.domain.model.Task;
import com.example.todo.domain.repository.UserRepositoryPort;

public class TaskMapper {
    private UserRepositoryPort userRepository;

    public Task toDomain(TaskDto taskDto) {
        return Task.builder()
            .id(taskDto.getId())
            .title(taskDto.getTitle())
            .description(taskDto.getDescription())
            .status(taskDto.getStatus())
            .assignedToUser(userRepository.findById(taskDto.getAssignedUserId()).orElseThrow())
            .owner(userRepository.findById(taskDto.getOwnerUserId()).orElseThrow())
            .build();
    }

    public TaskDto toDto(Task task) {
        return TaskDto.builder()
            .id(task.getId())
            .ownerUserId(task.getOwner().getId())
            .assignedUserId(task.getAssignedToUser().getId())
            .title(task.getTitle())
            .description(task.getDescription())
            .status(task.getStatus())
            .build();
    }
}

package com.example.todo.infrastructure.repository;

import com.example.todo.domain.model.Task;
import com.example.todo.domain.repository.TaskRepositoryPort;
import com.example.todo.infrastructure.model.TaskMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskRepositoryAdapter implements TaskRepositoryPort {
    JpaTaskRepository taskRepository;
    TaskMapper taskMapper;

    @Override
    public Optional<Task> findById(UUID taskId) {
        return taskRepository.findById(taskId).map(taskDto -> taskMapper.toDomain(taskDto));
    }

    @Override
    public List<Task> findByOwnerUserId(UUID ownerUserId) {
        return taskRepository.findByOwnerUserId(ownerUserId).stream().map(taskDto -> taskMapper.toDomain(taskDto)).toList();
    }

    @Override
    public List<Task> findByAssignedUserId(UUID assignedUserId) {
        return taskRepository.findByAssignedUserId(assignedUserId).stream().map(taskDto -> taskMapper.toDomain(taskDto)).toList();
    }

}

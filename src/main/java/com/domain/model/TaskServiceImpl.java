package com.domain.model;

import com.domain.repository.TaskRepositoryPort;
import com.domain.service.TaskService;

import java.math.BigInteger;
import java.util.List;

public class TaskServiceImpl implements TaskService {
    TaskRepositoryPort repository;

    public TaskServiceImpl(TaskRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Task getTasksById(BigInteger taskId) {
        return repository.findById(taskId).orElse(null);
    }

    @Override
    public List<Task> getTasksForUser(User user) {
        return this.repository.findByUserId(user.getId());
    }
}

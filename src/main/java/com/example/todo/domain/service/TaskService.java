package com.example.todo.domain.service;

import com.example.todo.domain.model.Task;
import com.example.todo.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task getTasksById(UUID taskId);
    List<Task> getTasksForUser(User user);
}

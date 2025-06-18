package com.example.todo.domain.service;

import com.example.todo.domain.model.Task;
import com.example.todo.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task createTask(Task task);
    Task getTaskById(UUID taskId);
    List<Task> getTasksByAssignedUser(UUID userId);
    Task updateTask(UUID taskId, Task taskUpdate);
    boolean deleteTask(UUID taskId);
    List<Task> getTasksForUser(User user);
}

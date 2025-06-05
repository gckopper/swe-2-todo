package com.domain.service;

import com.domain.model.Task;
import com.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task getTasksById(UUID taskId);
    List<Task> getTasksForUser(User user);
}

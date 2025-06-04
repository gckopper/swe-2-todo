package com.domain.service;

import com.domain.model.Task;
import com.domain.model.User;

import java.math.BigInteger;
import java.util.List;

public interface TaskService {
    Task getTasksById(BigInteger taskId);
    List<Task> getTasksForUser(User user);
}

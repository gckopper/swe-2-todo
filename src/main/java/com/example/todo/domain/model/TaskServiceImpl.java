package com.example.todo.domain.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.OffsetDateTime;

import com.example.todo.domain.service.CalendarService;
import com.example.todo.domain.service.TaskService;
import org.springframework.stereotype.Service;

import com.example.todo.domain.repository.TaskRepositoryPort;
import com.example.todo.domain.repository.UserRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepositoryPort taskRepository;
    private final UserRepositoryPort userRepository;
    private final CalendarService calendarService;

    @Override
    public Task createTask(Task task) {
        if (task.getOwner() == null || task.getOwner().getId() == null) {
            throw new IllegalArgumentException("Task must have an owner");
        }
        
        Optional<User> owner = userRepository.findById(task.getOwner().getId());
        if (owner.isEmpty()) {
            throw new IllegalArgumentException("Owner user not found");
        }
        task.setOwner(owner.get());
        
        if (task.getAssignedToUser() != null && task.getAssignedToUser().getId() != null) {
            Optional<User> assignedUser = userRepository.findById(task.getAssignedToUser().getId());
            if (assignedUser.isEmpty()) {
                throw new IllegalArgumentException("Assigned user not found");
            }
            task.setAssignedToUser(assignedUser.get());
        }
        
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.BACKLOG);
        }
        
        var calendarEvent = calendarService.insertCompletionDateEvent(task);
        if (calendarEvent != null && calendarEvent.getExternalEventId() != null) {
            task.setExternalCalendarEventId(calendarEvent.getExternalEventId());
        }
        
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(UUID taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public List<Task> getTasksByAssignedUser(UUID userId) {
        return taskRepository.findByAssignedUserId(userId);
    }

    @Override
    public Task updateTask(UUID taskId, Task taskUpdate) {
        Optional<Task> existingTask = taskRepository.findById(taskId);
        if (existingTask.isEmpty()) {
            return null;
        }
        
        Task task = existingTask.get();
        
        if (taskUpdate.getAssignedToUser() != null) {
            Optional<User> assignedUser = userRepository.findById(taskUpdate.getAssignedToUser().getId());
            if (assignedUser.isEmpty()) {
                throw new IllegalArgumentException("Assigned user not found");
            }

            boolean currentAssignedUserChanged = task.getAssignedToUser() != null 
                && task.getAssignedToUser().getId() != assignedUser.get().getId();

            if (currentAssignedUserChanged) {
                calendarService.deleteCompletionDateEvent(task);
                task.setExternalCalendarEventId(null);
            }
           
            task.setAssignedToUser(assignedUser.get());
        } else {
            calendarService.deleteCompletionDateEvent(task);
            task.setAssignedToUser(null);
        }
        
        if (taskUpdate.getTitle() != null) {
            task.setTitle(taskUpdate.getTitle());
        }

        if (taskUpdate.getDescription() != null) {
            task.setDescription(taskUpdate.getDescription());
        }

        if (taskUpdate.getStatus() != null) {
            task.setStatus(taskUpdate.getStatus());
        }

        task.setExpectedCompletionDate(taskUpdate.getExpectedCompletionDate());
        
        calendarService.editCompletionDateEvent(task);
        
        return taskRepository.save(task);
    }

    @Override
    public boolean deleteTask(UUID taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            return false;
        }
        
        calendarService.deleteCompletionDateEvent(task.get());
        
        taskRepository.deleteById(taskId);
        return true;
    }

    @Override
    public List<Task> getTasksForUser(User user) {
        return this.taskRepository.findByAssignedUserId(user.getId());
    }

    @Override
    public List<Task> getTasksByAssignedUserWithFilters(UUID userId, TaskStatus status, OffsetDateTime dueBefore, String title) {
        List<Task> tasks = taskRepository.findByAssignedUserId(userId);
        
        if (status != null) {
            tasks = tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
        }
        
        if (dueBefore != null) {
            tasks = tasks.stream()
                .filter(task -> task.getExpectedCompletionDate() != null && task.getExpectedCompletionDate().isBefore(dueBefore))
                .toList();
        }
        
        if (title != null && !title.trim().isEmpty()) {
            tasks = tasks.stream()
                .filter(task -> task.getTitle() != null && task.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
        }
        
        return tasks;
    }
} 
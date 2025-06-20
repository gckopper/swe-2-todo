package com.example.todo.domain.model;


import com.example.todo.domain.repository.TaskRepositoryPort;
import com.example.todo.domain.repository.UserRepositoryPort;
import com.example.todo.domain.service.CalendarService;
import com.example.todo.domain.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TaskServiceImplTest {
    TaskService taskService;
    @Mock
    TaskRepositoryPort taskRepository;
    @Mock
    UserRepositoryPort userRepository;
    @Mock
    CalendarService calendarService;

    AutoCloseable closableMocks;

    @BeforeEach
    public void setup(){
        closableMocks = MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(taskRepository, userRepository, calendarService);
    }
    @AfterEach
    public void teardown() throws Exception {
        closableMocks.close();
    }

    @Test
    public void createTaskWithEmptyOwner(){
        Task task = Task.builder().build();
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                taskService.createTask(task);
            });
    }

    @Test
    public void createTaskWithInvalidOwner(){
        Task task = Task.builder()
                .owner(User.builder()
                        .id(UUID.randomUUID())
                        .build())
                .build();

        when(userRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    taskService.createTask(task);
                });
    }
    @Test
    public void createTask(){
        User user = User.builder()
                .id(UUID.randomUUID())
                .build();
        Task task = Task.builder()
                .owner(User.builder()
                        .id(UUID.randomUUID())
                        .build())
                .title("Tit")
                .description("Desc")
                .build();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        Task expected = Task.builder()
                .id(result.getId())
                .status(TaskStatus.BACKLOG)
                .owner(user)
                .title("Tit")
                .description("Desc")
                .build();
        Assertions.assertEquals(expected, result);
    }


    @Test
    public void taskById(){
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .build();
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        Assertions.assertEquals(task,taskService.getTaskById(task.getId()));
    }
    @Test
    public void nonExistingTaskById(){
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .build();
        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

        Assertions.assertNull(taskService.getTaskById(task.getId()));
    }

    @Test
    public void taskByAssignedUser(){
        UUID assignedUserId = UUID.randomUUID();
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .assignedToUser(User.builder().id(assignedUserId).build())
                .build();
        when(taskRepository.findByAssignedUserId(assignedUserId)).thenReturn(List.of(task));

        Assertions.assertEquals(task, taskService.getTasksByAssignedUser(assignedUserId).get(0));
    }
    @Test
    public void updateNonExistingTask(){
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .build();
        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

        Assertions.assertNull(taskService.updateTask(task.getId(), task));
    }
    @Test
    public void updateNonAssignedTask(){
        User user = User.builder()
                .id(UUID.randomUUID())
                .build();
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .owner(user)
                .title("Abc")
                .description("Desc")
                .build();
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task expected = Task.builder()
                .id(task.getId())
                .owner(user)
                .title("Abc")
                .description("Desc")
                .build();
        Assertions.assertEquals(expected, taskService.updateTask(task.getId(), task));
    }

    @Test
    public void deleteNonExistingTask(){
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .build();
        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

        Assertions.assertFalse(taskService.deleteTask(task.getId()));
    }

    @Test
    public void advancedFilter(){
        UUID userId = UUID.randomUUID();
        User user = User.builder().id(userId).build();
        List<Task> lis = List.of(
                Task.builder().id(UUID.randomUUID()).assignedToUser(user).build(),
                Task.builder().id(UUID.randomUUID()).assignedToUser(user).build(),
                Task.builder().id(UUID.randomUUID()).assignedToUser(user).build(),
                Task.builder().id(UUID.randomUUID()).assignedToUser(user).build(),
                Task.builder().id(UUID.randomUUID()).assignedToUser(user).build()
        );
        when(taskRepository.findByAssignedUserId(userId)).thenReturn(lis);
        List<Task> tasks = taskService.getTasksByAssignedUserWithFilters(userId, null, null, null );
        Assertions.assertEquals(lis, tasks);
    }
    @Test
    public void advancedFilterWithTitle(){
        UUID userId = UUID.randomUUID();
        User user = User.builder().id(userId).build();
        List<Task> lis = List.of(
                Task.builder().id(UUID.randomUUID()).title("ABC").assignedToUser(user).build(),
                Task.builder().id(UUID.randomUUID()).title("BCD").assignedToUser(user).build(),
                Task.builder().id(UUID.randomUUID()).title("CDE").assignedToUser(user).build(),
                Task.builder().id(UUID.randomUUID()).title("DEF").assignedToUser(user).build(),
                Task.builder().id(UUID.randomUUID()).title("EFG").assignedToUser(user).build()
        );
        when(taskRepository.findByAssignedUserId(userId)).thenReturn(lis);
        List<Task> tasks = taskService.getTasksByAssignedUserWithFilters(userId, null, null, "C" );
        List<Task> expected = List.of(
                lis.get(0), lis.get(1), lis.get(2)
        );
        Assertions.assertEquals(expected, tasks);
    }

}
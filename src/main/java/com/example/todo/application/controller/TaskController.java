package com.example.todo.application.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.application.dto.CreateTaskRequest;
import com.example.todo.application.dto.TaskResponse;
import com.example.todo.application.dto.UpdateTaskRequest;
import com.example.todo.application.mapper.TaskApiMapper;
import com.example.todo.domain.model.Task;
import com.example.todo.domain.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(
        summary = "Criar uma nova tarefa",
        description = "Cria uma nova tarefa no sistema. O ID da tarefa é gerado automaticamente."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso")
    })
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        Task task = TaskApiMapper.toDomain(request);
        Task createdTask = taskService.createTask(task);
        TaskResponse response = TaskApiMapper.toResponse(createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obter uma tarefa por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable UUID id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        TaskResponse response = TaskApiMapper.toResponse(task);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar tarefas atribuídas a um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksByAssignedUser(@RequestParam UUID assignedTo) {
        List<Task> tasks = taskService.getTasksByAssignedUser(assignedTo);
        List<TaskResponse> responses = TaskApiMapper.toResponseList(tasks);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Atualizar uma tarefa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable UUID id, @RequestBody UpdateTaskRequest request) {
        Task task = TaskApiMapper.toDomain(id, request);
        Task updatedTask = taskService.updateTask(id, task);
        TaskResponse response = TaskApiMapper.toResponse(updatedTask);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remover uma tarefa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tarefa removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        boolean deleted = taskService.deleteTask(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

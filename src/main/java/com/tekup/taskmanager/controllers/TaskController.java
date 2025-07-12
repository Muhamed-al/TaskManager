package com.tekup.taskmanager.controllers;

import com.tekup.taskmanager.dto.TaskRequestDTO;
import com.tekup.taskmanager.dto.TaskResponseDTO;
import com.tekup.taskmanager.services.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v0/tasks")
@Tag(name = "Tasks", description = "Task management APIs")
public class TaskController {

    private ITaskService taskService;

    @Autowired
    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create a new task", description = "Creates a new task with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO dto) {
        return ResponseEntity.ok(taskService.createTask(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO dto) {
        return ResponseEntity.ok(taskService.updateTask(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID", description = "Retrieves a specific task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TaskResponseDTO> getTask(
            @Parameter(description = "Task ID", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Retrieves all tasks in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByStatus(@PathVariable String status) {
        return ResponseEntity.ok(taskService.searchByStatus(status));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByPriority(@PathVariable String priority) {
        return ResponseEntity.ok(taskService.searchByPriority(priority));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByUser(userId));
    }

    @GetMapping("/due-before")
    public ResponseEntity<List<TaskResponseDTO>> getTasksBeforeDueDate(@RequestParam String date) {
        return ResponseEntity.ok(taskService.getTasksBeforeDueDate(LocalDate.parse(date)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> searchTasks(@RequestParam String keyword) {
        return ResponseEntity.ok(taskService.searchTasksByKeyword(keyword));
    }

    @GetMapping("/sorted/due-date")
    public ResponseEntity<List<TaskResponseDTO>> getTasksSortedByDueDate() {
        return ResponseEntity.ok(taskService.getTasksSortedByDueDate());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam String status) {
        taskService.changeTaskStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<Void> assignUser(@PathVariable Long taskId, @PathVariable Long userId) {
        taskService.assignUserToTask(taskId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{taskId}/remove/{userId}")
    public ResponseEntity<Void> removeUser(@PathVariable Long taskId, @PathVariable Long userId) {
        taskService.removeUserFromTask(taskId, userId);
        return ResponseEntity.ok().build();
    }
}

package com.tekup.taskmanager.services;

import com.tekup.taskmanager.dto.TaskRequestDTO;
import com.tekup.taskmanager.dto.TaskResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ITaskService {


    TaskResponseDTO createTask(TaskRequestDTO dto);
    TaskResponseDTO updateTask(Long id, TaskRequestDTO dto);
    void deleteTask(Long id);
    TaskResponseDTO getTaskById(Long id);
    List<TaskResponseDTO> getAllTasks();
    List<TaskResponseDTO> searchByStatus(String status);
    List<TaskResponseDTO> searchByPriority(String priority);
    List<TaskResponseDTO> getTasksByProject(Long projectId);
    List<TaskResponseDTO> getTasksByUser(Long userId);
    List<TaskResponseDTO> getTasksBeforeDueDate(LocalDate date);
    List<TaskResponseDTO> searchTasksByKeyword(String keyword);
    List<TaskResponseDTO> getTasksSortedByDueDate();
    void changeTaskStatus(Long id, String status);
    void assignUserToTask(Long taskId, Long userId);
    void removeUserFromTask(Long taskId, Long userId);




}

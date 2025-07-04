package com.tekup.taskmanager.services;


import com.tekup.taskmanager.dto.AssignedUserDTO;
import com.tekup.taskmanager.dto.ProjectDTO;
import com.tekup.taskmanager.dto.TaskRequestDTO;
import com.tekup.taskmanager.dto.TaskResponseDTO;
import com.tekup.taskmanager.entities.Project;
import com.tekup.taskmanager.entities.Task;
import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.exception.ResourceNotFoundException;
import com.tekup.taskmanager.repositories.ProjectRepository;
import com.tekup.taskmanager.repositories.TaskRepository;
import com.tekup.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }
    @Override
    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setProject(project);

        if (dto.getAssignedUserIds() != null && !dto.getAssignedUserIds().isEmpty()) {
            Set<User> users = userRepository.findAllById(dto.getAssignedUserIds())
                    .stream().collect(Collectors.toSet());
            task.setAssignedUsers(users);
        }

        Task saved = taskRepository.save(task);
        return mapToResponseDTO(saved);
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        task.setProject(project);

        if (dto.getAssignedUserIds() != null) {
            Set<User> users = userRepository.findAllById(dto.getAssignedUserIds())
                    .stream().collect(Collectors.toSet());
            task.setAssignedUsers(users);
        }

        return mapToResponseDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {
        return taskRepository.findById(id).map(this::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> searchByStatus(String status) {
        return taskRepository.findByStatus(status).stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> searchByPriority(String priority) {
        return taskRepository.findByPriority(priority).stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedUsers_Id(userId).stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> getTasksBeforeDueDate(LocalDate date) {
        return taskRepository.findByDueDateBefore(date).stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> searchTasksByKeyword(String keyword) {
        return taskRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> getTasksSortedByDueDate() {
        return taskRepository.findAllByOrderByDueDateAsc().stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void changeTaskStatus(Long id, String status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setStatus(status);
        taskRepository.save(task);
    }

    @Override
    public void assignUserToTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        task.getAssignedUsers().add(user);
        taskRepository.save(task);
    }

    @Override
    public void removeUserFromTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.getAssignedUsers().removeIf(user -> user.getId().equals(userId));
        taskRepository.save(task);
    }

    private TaskResponseDTO mapToResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(task.getProject().getName());
        projectDTO.setDescription(task.getProject().getDescription());
        projectDTO.setStartDate(task.getProject().getStartDate());
        projectDTO.setEndDate(task.getProject().getEndDate());
        projectDTO.setStatus(task.getProject().getStatus());
        dto.setProject(projectDTO);
        if (task.getAssignedUsers() != null) {
            Set<AssignedUserDTO> assignedUserDTOS = task.getAssignedUsers().stream().map(user -> {
                AssignedUserDTO userDTO = new AssignedUserDTO();
                userDTO.setUserId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                userDTO.setRoles(user.getRoles().stream().toList());
                return userDTO;
            }).collect(Collectors.toSet());
            dto.setAssignedUsers(assignedUserDTOS);
        }
        return dto;
    }
}

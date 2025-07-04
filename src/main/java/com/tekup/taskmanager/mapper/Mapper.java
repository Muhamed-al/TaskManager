package com.tekup.taskmanager.mapper;

import com.tekup.taskmanager.dto.AssignedUserDTO;
import com.tekup.taskmanager.dto.TaskResponseDTO;
import com.tekup.taskmanager.dto.ProjectDTO;
import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.entities.Task;
import com.tekup.taskmanager.entities.Project;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    public static AssignedUserDTO toAssignedUserDTO(User user) {
        if (user == null) return null;
        AssignedUserDTO dto = new AssignedUserDTO();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles() != null ? List.copyOf(user.getRoles()) : null);
        return dto;
    }

    public static ProjectDTO toProjectDTO(Project project) {
        if (project == null) return null;
        ProjectDTO dto = new ProjectDTO();
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setStatus(project.getStatus());
        return dto;
    }

    public static TaskResponseDTO toTaskResponseDTO(Task task) {
        if (task == null) return null;
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setProject(toProjectDTO(task.getProject()));
        if (task.getAssignedUsers() != null) {
            Set<AssignedUserDTO> assignedUserDTOs = task.getAssignedUsers().stream()
                .map(Mapper::toAssignedUserDTO)
                .collect(Collectors.toSet());
            dto.setAssignedUsers(assignedUserDTOs);
        }
        return dto;
    }
}

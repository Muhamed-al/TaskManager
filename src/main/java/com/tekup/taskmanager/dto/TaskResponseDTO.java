package com.tekup.taskmanager.dto;

import com.tekup.taskmanager.entities.Project;
import com.tekup.taskmanager.entities.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;


@Data
public class TaskResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private ProjectDTO project;
    private Set<AssignedUserDTO> assignedUsers;
}
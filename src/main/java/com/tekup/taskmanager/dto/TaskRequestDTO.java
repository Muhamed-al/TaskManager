package com.tekup.taskmanager.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class TaskRequestDTO {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    @FutureOrPresent
    private LocalDate dueDate;

    @NotBlank
    private String status;

    @NotBlank
    private String priority;

    @NotNull
    private Long projectId;

    private Set<Long> assignedUserIds;
}
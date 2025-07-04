package com.tekup.taskmanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotBlank(message = "Task name cannot be empty")
    @Size(min = 3, max = 100, message = "Task name must be between 3 and 100 characters")
    private String name;
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;


    @NotNull(message = "Due date cannot be null")
    @FutureOrPresent(message = "Due date must be in the presentor future")
    private LocalDate dueDate;
    @NotBlank(message = "Status cannot be empty")
    private String status;
    @NotBlank(message = "Priority cannot be empty")
    private String priority;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToMany
    @JoinTable(name = "task_assigned_users",
    joinColumns = @JoinColumn(name = "task_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> assignedUsers;


    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Document> documents;


    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Comment> comments;
}
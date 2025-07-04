package com.tekup.taskmanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Entity
@Data
public class Document {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank(message = "Filename cannot be empty")
    @Size(max = 255, message = "Filename cannot exceed 255 characters")
    private String filename;
    @NotBlank(message = "File type cannot be empty")
    private String fileType;
    @NotBlank(message = "S3 URL cannot be empty")
    private String s3Url;


    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
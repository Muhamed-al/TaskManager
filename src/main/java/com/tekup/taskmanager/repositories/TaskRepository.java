package com.tekup.taskmanager.repositories;

import com.tekup.taskmanager.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(String status);

    List<Task> findByPriority(String priority);

    List<Task> findByProjectId(Long projectId);

    List<Task> findByAssignedUsers_Id(Long userId);

    List<Task> findByDueDateBefore(LocalDate date);

    List<Task> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword, String keyword1);

    List<Task> findAllByOrderByDueDateAsc();
}
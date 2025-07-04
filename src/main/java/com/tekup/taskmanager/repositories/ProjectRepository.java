package com.tekup.taskmanager.repositories;

import com.tekup.taskmanager.entities.Project;
import com.tekup.taskmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(String status);
    List<Project> findByStartDateAfter(LocalDate now);
    List<Project> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword, String keyword1);
    List<Project> findByTasksAssignedUsersContains(User user);
}

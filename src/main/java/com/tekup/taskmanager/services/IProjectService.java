package com.tekup.taskmanager.services;

import com.tekup.taskmanager.entities.Project;
import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.exception.UserNotFoundException;

import java.util.List;

public interface IProjectService {

    public Project createProject(Project project);
    public Project updateProject(Long id, Project project);
    public void deleteProject(Long id);
    public List<Project> getAllProjects();
    public List<Project> getProjectsByStatus(String status);
    public List<Project> getUpcomingProjects();
    public List<Project> searchProjects(String keyword);
    public List<Project> getProjectsByUserParticipation(Long userId) throws UserNotFoundException;
}

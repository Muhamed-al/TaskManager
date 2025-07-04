package com.tekup.taskmanager.services;


import com.tekup.taskmanager.entities.Project;
import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.exception.ResourceNotFoundException;
import com.tekup.taskmanager.exception.UserNotFoundException;
import com.tekup.taskmanager.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService {

    private ProjectRepository projectRepository;
    private final IUserService userService;
    public ProjectServiceImpl(ProjectRepository projectRepository, IUserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project project) {
        Project p = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        p.setName(project.getName());
        p.setDescription(project.getDescription());
        p.setStartDate(project.getStartDate());
        p.setEndDate(project.getEndDate());
        p.setStatus(project.getStatus());
        return projectRepository.save(p);
    }

    @Override
    public void deleteProject(Long id) {
        Project p = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        projectRepository.delete(p);
    }
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    @Override
    public List<Project> getProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }
    @Override
    public List<Project> getUpcomingProjects() {
        return projectRepository.findByStartDateAfter(LocalDate.now());
    }
    @Override
    public List<Project> searchProjects(String keyword) {
        return projectRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }
    @Override
    public List<Project> getProjectsByUserParticipation(Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return projectRepository.findByTasksAssignedUsersContains(user);
    }
}

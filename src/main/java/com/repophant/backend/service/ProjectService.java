package com.repophant.backend.service;

import com.repophant.backend.domain.Project;
import com.repophant.backend.repository.ProjectRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

  @Autowired private ProjectRepository projectRepository;

  public List<Project> getAllProjects() {
    return projectRepository.findAll();
  }

  public Project getProjectById(Long id) {
    return projectRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Project with ID " + id + " not found"));
  }

  public Project createProject(Project project) {
    return projectRepository.save(project);
  }

  public Project updateProject(Long id, Project updatedProject) {
    return projectRepository
        .findById(id)
        .map(
            project -> {
              project.setName(updatedProject.getName());
              project.setDescription(updatedProject.getDescription());
              return projectRepository.save(project);
            })
        .orElseThrow(() -> new IllegalArgumentException("Project with ID " + id + " not found"));
  }

  public void deleteProject(Long id) {
    if (!projectRepository.existsById(id)) {
      throw new IllegalArgumentException("Cannot delete. Project with ID " + id + " not found.");
    }
    projectRepository.deleteById(id);
  }
}

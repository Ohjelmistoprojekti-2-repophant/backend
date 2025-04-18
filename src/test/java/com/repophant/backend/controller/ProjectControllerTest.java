package com.repophant.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.repophant.backend.domain.Project;
import com.repophant.backend.service.ProjectService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ProjectControllerTest {

  @Mock private ProjectService projectService;

  @InjectMocks private ProjectController projectController;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
  }

  @Test
  public void testGetAllProjects() {
    Project project1 = new Project();
    Project project2 = new Project();
    List<Project> projects = Arrays.asList(project1, project2);

    when(projectService.getAllProjects()).thenReturn(projects);

    ResponseEntity<List<Project>> response = projectController.getAllProjects();

    assertNotNull(response.getBody(), "Response body is null");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody(), "Response body is null");
    assertEquals(2, response.getBody().size());
    assertEquals(project1, response.getBody().get(0));
    assertEquals(project2, response.getBody().get(1));
  }

  @Test
  public void testGetProjectById() {
    Project project = new Project();
    when(projectService.getProjectById(anyLong())).thenReturn(project);

    ResponseEntity<Project> response = projectController.getProjectById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(project, response.getBody());
  }

  @Test
  public void testGetProjectById_NotFound() {
    when(projectService.getProjectById(anyLong())).thenReturn(null);

    ResponseEntity<Project> response = projectController.getProjectById(1L);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody(), "Response body should be null");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void testCreateProject() {
    Project project = new Project();
    when(projectService.createProject(any(Project.class))).thenReturn(project);

    ResponseEntity<Project> response = projectController.createProject(project);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(project, response.getBody());
  }

  @Test
  public void testUpdateProject() {
    Project project = new Project();
    when(projectService.updateProject(anyLong(), any(Project.class))).thenReturn(project);

    ResponseEntity<Project> response = projectController.updateProject(1L, project);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(project, response.getBody());
  }

  public void testDeleteProject() {
    ResponseEntity<Void> response = projectController.deleteProject(1L);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(projectService).deleteProject(1L);
  }
}

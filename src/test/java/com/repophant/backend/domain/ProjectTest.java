package com.repophant.backend.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;

public class ProjectTest {

  @Test
  public void testDefaultConstructor() {
    Project project = new Project();
    assertNull(project.getId());
    assertNull(project.getName());
    assertNull(project.getDescription());
    assertNull(project.getLanguage());
    assertNull(project.getRepositoryLink());
    assertNull(project.getCreatedAt());
    assertNull(project.getPushedAt());
  }

  @Test
  public void testParameterizedConstructor() {
    Project project =
        new Project(
            "Project Name",
            "Project Description",
            "Java",
            "http://repository.link",
            Instant.parse("2022-02-01T00:00:00Z"),
            Instant.parse("2022-02-01T00:00:00Z"));
    assertNull(project.getId());
    assertEquals("Project Name", project.getName());
    assertEquals("Project Description", project.getDescription());
    assertEquals("Java", project.getLanguage());
    assertEquals("http://repository.link", project.getRepositoryLink());
    assertEquals(Instant.parse("2022-02-01T00:00:00Z"), project.getCreatedAt());
    assertEquals(Instant.parse("2022-02-01T00:00:00Z"), project.getPushedAt());
  }

  @Test
  public void testSetName() {
    Project project = new Project();
    project.setName("New Project Name");
    assertEquals("New Project Name", project.getName());
  }

  @Test
  public void testSetDescription() {
    Project project = new Project();
    project.setDescription("New Project Description");
    assertEquals("New Project Description", project.getDescription());
  }

  @Test
  public void testSetLanguage() {
    Project project = new Project();
    project.setLanguage("Python");
    assertEquals("Python", project.getLanguage());
  }

  @Test
  public void testSetRepositoryLink() {
    Project project = new Project();
    project.setRepositoryLink("http://new.repository.link");
    assertEquals("http://new.repository.link", project.getRepositoryLink());
  }

  @Test
  public void testSetCreatedAt() {
    Project project = new Project();
    Instant now = Instant.now();
    project.setCreatedAt(now);
    assertEquals(now, project.getCreatedAt());
  }

  @Test
  public void testSetPushedAt() {
    Project project = new Project();
    Instant now = Instant.now();
    project.setPushedAt(now);
    assertEquals(now, project.getPushedAt());
  }

  @Test
  public void testProjectCreation() {
    Project project = new Project();
    Instant fixedTime = Instant.parse("2024-01-01T00:00:00Z");
    project.setCreatedAt(fixedTime);
    project.setPushedAt(fixedTime);
    assertEquals(fixedTime, project.getCreatedAt());
    assertEquals(fixedTime, project.getPushedAt());
  }
}

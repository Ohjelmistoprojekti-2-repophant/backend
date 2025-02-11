package com.repophant.backend.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ProjectTest {

    @Test
    public void testDefaultConstructor() {
        Project project = new Project();
        assertNull(project.getId());
        assertNull(project.getName());
        assertNull(project.getDescription());
        assertNull(project.getLanguage());
        assertNull(project.getRepositoryLink());
    }

    @Test
    public void testParameterizedConstructor() {
        Project project = new Project("Project Name", "Project Description", "Java", "http://repository.link");
        assertNull(project.getId());
        assertEquals("Project Name", project.getName());
        assertEquals("Project Description", project.getDescription());
        assertEquals("Java", project.getLanguage());
        assertEquals("http://repository.link", project.getRepositoryLink());
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
    public void testEqualsAndHashCode() {
        Project project1 = new Project("Project Name", "Project Description", "Java", "http://repository.link");
        Project project2 = new Project("Project Name", "Project Description", "Java", "http://repository.link");
        project1.setId(1L);
        project2.setId(1L);
        assertEquals(project1, project2);
        assertEquals(project1.hashCode(), project2.hashCode());
    }

    @Test
    public void testToString() {
        Project project = new Project("Project Name", "Project Description", "Java", "http://repository.link");
        project.setId(1L);
        String expected = "Project{id=1, name='Project Name', description='Project Description', language='Java', repositoryLink='http://repository.link'}";
        assertEquals(expected, project.toString());
    }
}
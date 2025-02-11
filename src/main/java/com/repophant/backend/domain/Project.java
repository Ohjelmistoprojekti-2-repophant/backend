package com.repophant.backend.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.GenerationType;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;

    @NotBlank(message = "Language is required")
    private String language;

    @NotBlank(message = "Repository link is required")
    @Size(max = 200, message = "Repository link must be less than 200 characters")
    private String repositoryLink;

    // Default constructor required by JPA
    public Project() {
    }

    // Parameterized constructor to initialize Project with name, description, language, and repository link
    public Project(String name, String description, String language, String repositoryLink) {
        this.name = name;
        this.description = description;
        this.language = language;
        this.repositoryLink = repositoryLink;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString method
    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", repositoryLink='" + repositoryLink + '\'' +
                '}';
    }
}
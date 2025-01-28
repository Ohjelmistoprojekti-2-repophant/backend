package com.repophant.backend.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    // Default constructor required by JPA
    public Project() {
    }
    // Parameterized constructor to initialize Project with name and description
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
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
    /**
     * Sets the name of the project.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of the project.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
                '}';
    }
}

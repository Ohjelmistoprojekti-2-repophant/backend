package com.repophant.backend.domain;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title should not exceed 100 characters")
    private String title;

    @Lob
    @NotBlank(message = "Text body is required")
    private String body;

    // temporary until user entity is implemented
    @NotBlank(message = "Username is required")
    private String poster;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;

    private Instant postedAt;

    public Review() {
        this.postedAt = Instant.now();
    }

    public Review(Long id, String title, String body, String user, Project project) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.poster = user;
        this.project = project;
        this.postedAt = Instant.now();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String user) {
        this.poster = user;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Instant getPostedAt() {
        return this.postedAt;
    }

    public void setPostedAt(Instant datetime) {
        this.postedAt = datetime;
    }


    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", title='" + getTitle() + "'" +
                ", body='" + getBody() + "'" +
                ", user='" + getPoster() + "'" +
                ", datetime='" + getPostedAt() + "'" +
                "}";
    }

}

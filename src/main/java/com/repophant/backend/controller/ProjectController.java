package com.repophant.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.repophant.backend.domain.Project;
import com.repophant.backend.service.ProjectService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
@Tag(name = "Project Viewing Application", description = "Operations for managing projects")
public class ProjectController {
    

    private final ProjectService projectService;

    // Constructor-based dependency injection is used instead of field-based injection
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Return a list of available projects", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of projects"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
    @Operation(summary = "Get a project by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved project"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@Parameter(description = "ID of project to be searched") @PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(project);
    }

    @Operation(summary = "Add a project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Project created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to create the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to create is forbidden")
    })
    @PostMapping
    public ResponseEntity<Project> createProject(@Parameter(description = "Project to be created") @RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @Operation(summary = "Update a project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to update the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to update is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to update is not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@Parameter(description = "ID of project to be updated") @PathVariable Long id, 
                                                 @Parameter(description = "Updated project details") @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(id, project);
        return ResponseEntity.ok(updatedProject);
    }

    @Operation(summary = "Delete a project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to delete the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to delete is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to delete is not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@Parameter(description = "ID of project to be deleted") @PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
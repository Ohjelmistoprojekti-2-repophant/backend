package com.repophant.backend;

import com.repophant.backend.domain.Project;
import com.repophant.backend.domain.Review;
import com.repophant.backend.repository.ProjectRepository;
import com.repophant.backend.repository.ReviewRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleDataInitializer {

  @Bean
  public CommandLineRunner initData(
      ProjectRepository projectRepository, ReviewRepository reviewRepository) {
    return args -> {
      reviewRepository.deleteAll();
      projectRepository.deleteAll();
      if (projectRepository.count() == 0) {

        // Sample projects
        Project p1 = new Project();
        p1.setName("Hourbook");
        p1.setDescription("Work Hour Management System.");
        p1.setLanguage("Java");
        p1.setRepositoryLink("https://github.com/kvilho/nowhitespaces");
        p1.setCreatedAt(Instant.parse("2024-12-01T00:00:00Z"));
        p1.setPushedAt(Instant.parse("2024-12-01T00:00:00Z"));

        Project p2 = new Project();
        p2.setName("MacroHub");
        p2.setDescription("Script management platform.");
        p2.setLanguage("Java");
        p2.setRepositoryLink("https://github.com/Team-410/MacroHub");
        p2.setCreatedAt(Instant.parse("2023-10-01T00:00:00Z"));
        p2.setPushedAt(Instant.parse("2023-10-01T00:00:00Z"));
        Project p3 = new Project();
        p3.setName("Repophant Frontend");
        p3.setDescription(
            "Frontend for the Repophant project, built with modern web technologies.");
        p3.setLanguage("JavaScript");
        p3.setRepositoryLink("https://github.com/Ohjelmistoprojekti-2-repophant/frontend");
        p3.setCreatedAt(Instant.parse("2022-08-01T00:00:00Z"));
        p3.setPushedAt(Instant.parse("2022-08-01T00:00:00Z"));

        Project p4 = new Project();
        p4.setName("Repophant Backend");
        p4.setDescription("Backend for the Repophant project, built with Spring Boot.");
        p4.setLanguage("Java");
        p4.setRepositoryLink("https://github.com/Ohjelmistoprojekti-2-repophant/backend");
        p4.setCreatedAt(Instant.parse("2021-06-01T00:00:00Z"));
        p4.setPushedAt(Instant.parse("2021-06-01T00:00:00Z"));

        Project p5 = new Project();
        p5.setName("Taskmaster");
        p5.setDescription(
            "Taskmaster will provide simple and fluent task and project management for groups and individuals.");
        p5.setLanguage("Java");
        p5.setRepositoryLink("https://github.com/teamroutine/taskmaster");
        p5.setCreatedAt(Instant.parse("2020-05-01T00:00:00Z"));
        p5.setPushedAt(Instant.parse("2020-05-01T00:00:00Z"));

        projectRepository.saveAll(List.of(p1, p2, p3, p4, p5));

        // Sample reviews
        Review r1 =
            new Review(
                "Very useful tool!",
                "This project helped me clean up my text files efficiently.",
                "alice",
                p1);
        Review r2 =
            new Review(
                "Great for students!",
                "The course planner is intuitive and easy to use.",
                "bob",
                p2);
        Review r3 =
            new Review("Impressive frontend!", "The UI is clean and responsive.", "charlie", p3);
        Review r4 =
            new Review(
                "Solid backend!", "The API is well-documented and easy to integrate.", "dave", p4);
        Review r5 =
            new Review(
                "Highly recommended!",
                "Taskmaster is a game-changer for project management.",
                "eve",
                p5);

        reviewRepository.saveAll(List.of(r1, r2, r3, r4, r5));
      }
    };
  }
}

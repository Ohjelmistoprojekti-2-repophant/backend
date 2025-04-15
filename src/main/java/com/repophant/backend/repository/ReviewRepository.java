package com.repophant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.repophant.backend.domain.Review;
import java.util.List;
import com.repophant.backend.domain.Project;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProject(Project project);
}

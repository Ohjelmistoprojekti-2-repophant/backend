package com.repophant.backend.repository;

import com.repophant.backend.domain.Project;
import com.repophant.backend.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByProject(Project project);
}

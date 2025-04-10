package com.repophant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.repophant.backend.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}

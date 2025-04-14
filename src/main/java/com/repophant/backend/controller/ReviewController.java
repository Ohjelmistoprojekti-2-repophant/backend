package com.repophant.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.repophant.backend.domain.Review;
import com.repophant.backend.repository.ProjectRepository;
import com.repophant.backend.repository.ReviewRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class ReviewController {

    @Autowired
    private ReviewRepository revrepo;

    @Autowired
    private ProjectRepository prorepo;

    @GetMapping("/api/reviews/{id}")
    public Iterable<Review> getMethodName(@PathVariable("id") Long projectid) {
        return revrepo.findByProject(prorepo.findById(projectid).get());
    }
    
    
}

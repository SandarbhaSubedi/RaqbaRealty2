package com.smart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.ReviewRepository;
import com.smart.entities.Review;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // Admin: Get all reviews regardless of approval status
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Admin or user: Save review (default approved = false in entity or controller)
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    // Admin: Delete review by id
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    // User: Get latest 5 approved reviews
    public List<Review> getLatestApprovedReviews() {
        return reviewRepository.findTop5ByApprovedTrueOrderByIdDesc();
    }

    // User: Get all approved reviews (optionally paginated or sorted)
    public List<Review> getAllApprovedReviews() {
        return reviewRepository.findByApprovedTrueOrderByIdDesc();
    }
}

package com.example.eventmanagement.service;

import com.example.eventmanagement.model.Review;

import com.example.eventmanagement.repository.ReviewRepository;
import com.example.eventmanagement.repository.VenueRepository;
import com.example.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private UserRepository userRepository;

    public Review addReview(Long userId, Long venueId, int rating, String comment) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        var venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        Review review = new Review();
        review.setUser(user);
        review.setVenue(venue);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForVenue(Long venueId) {
        var venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
        return reviewRepository.findByVenue(venue);
    }
}

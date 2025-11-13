package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.Review;
import com.example.eventmanagement.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Review addReview(@RequestParam Long userId,
                            @RequestParam Long venueId,
                            @RequestParam int rating,
                            @RequestParam String comment) {
        return reviewService.addReview(userId, venueId, rating, comment);
    }

    @GetMapping("/venue/{venueId}")
    public List<Review> getReviewsForVenue(@PathVariable Long venueId) {
        return reviewService.getReviewsForVenue(venueId);
    }
}

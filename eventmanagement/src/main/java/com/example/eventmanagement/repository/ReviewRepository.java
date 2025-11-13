package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Review;
import com.example.eventmanagement.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVenue(Venue venue);
}

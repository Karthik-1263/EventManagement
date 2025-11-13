package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findByVendorId(Long vendorId);
}

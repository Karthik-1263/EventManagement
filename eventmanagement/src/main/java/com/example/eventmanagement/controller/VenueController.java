package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.Venue;
import com.example.eventmanagement.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    // ✅ GET all venues (public)
    @GetMapping
    public ResponseEntity<?> getAllVenues() {
        List<Venue> venues = venueService.getAllVenues();
        if (venues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No venues found");
        }
        return ResponseEntity.ok(venues);
    }

    // ✅ POST - Vendor creates venue
    @PostMapping
    public ResponseEntity<?> addVenue(@RequestBody Venue venue, @RequestHeader("vendorId") Long vendorId) {
        try {
            venue.setVendorId(vendorId); // set from logged-in vendor
            Venue savedVenue = venueService.saveVenue(venue);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVenue);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // ✅ PUT - Vendor updates only his venue
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVenue(@PathVariable Long id, @RequestBody Venue venueDetails,
                                         @RequestHeader("vendorId") Long vendorId) {
        try {
            Venue updatedVenue = venueService.updateVenue(id, venueDetails, vendorId);
            return ResponseEntity.ok(updatedVenue);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    // ✅ DELETE - Vendor deletes only his venue
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVenue(@PathVariable Long id, @RequestHeader("vendorId") Long vendorId) {
        try {
            venueService.deleteVenue(id, vendorId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}

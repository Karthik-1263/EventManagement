package com.example.eventmanagement.service;

import com.example.eventmanagement.model.Venue;
import com.example.eventmanagement.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Venue saveVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public Venue updateVenue(Long id, Venue venueDetails, Long vendorId) {
        Venue existingVenue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
        System.out.println("Updating venue with ID: " + id + " by vendor: " + vendorId);
        if (!existingVenue.getVendorId().equals(vendorId)) {
            throw new RuntimeException("You are not authorized to update this venue" + vendorId);
        }

        existingVenue.setName(venueDetails.getName());
        existingVenue.setLocation(venueDetails.getLocation());
        existingVenue.setCapacity(venueDetails.getCapacity());
        existingVenue.setPricePerDay(venueDetails.getPricePerDay());

        return venueRepository.save(existingVenue);
    }

    public void deleteVenue(Long id, Long vendorId) {
        Venue existingVenue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        if (!existingVenue.getVendorId().equals(vendorId)) {
            throw new RuntimeException("You are not authorized to delete this venue");
        }

        venueRepository.delete(existingVenue);
    }
}

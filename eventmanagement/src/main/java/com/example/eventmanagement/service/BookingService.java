package com.example.eventmanagement.service;

import com.example.eventmanagement.model.Booking;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.model.Venue;
import com.example.eventmanagement.repository.BookingRepository;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired private BookingRepository bookingRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private VenueRepository venueRepository;

    public Booking saveBooking(Booking booking) {
        System.out.println("Saving booking for user ID: " + booking.getUser().getId() +
                " at venue ID: " + booking.getVenue().getId() );
        // Fetch proper references from DB to avoid detached entity issues
        User user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Venue venue = venueRepository.findById(booking.getVenue().getId())
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        booking.setUser(user);
        booking.setVenue(venue);
        booking.setStatus("CONFIRMED");

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}

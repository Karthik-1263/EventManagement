package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.Booking;
import com.example.eventmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> addBooking(@RequestBody Booking booking) {
        try {
            System.out.println("Adding booking for user ID: " + booking.getUser().getId() +
                    " at venue ID: " + booking.getVenue().getId());
            Booking savedBooking = bookingService.saveBooking(booking);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking); // 201 Created
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); // 400 Bad Request
        }
    }

    @GetMapping("/my/{userId}")
    public ResponseEntity<?> getMyBookings(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);

        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No bookings found for user with ID: " + userId);
        }

        return ResponseEntity.ok(bookings); // 200 OK
    }
}

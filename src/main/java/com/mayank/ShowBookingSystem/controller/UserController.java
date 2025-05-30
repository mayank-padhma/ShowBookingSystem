package com.mayank.ShowBookingSystem.controller;

import com.mayank.ShowBookingSystem.model.Booking;
import com.mayank.ShowBookingSystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final BookingService bookingService;

    @Autowired
    public UserController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping("/bookTicket")
    public String bookTicket(@RequestParam String user, @RequestParam String show, @RequestParam String time, @RequestParam int persons) {
        return bookingService.book(user, show, time, persons);
    }

    @PostMapping("/cancelBookingId")
    public String cancelBookingId(@RequestParam String bookingId) {
        return bookingService.cancel(bookingId);
    }

    @GetMapping("/bookings")
    public List<Booking> bookings(@RequestParam String user) {
        return bookingService.getUserBookings(user);
    }
}

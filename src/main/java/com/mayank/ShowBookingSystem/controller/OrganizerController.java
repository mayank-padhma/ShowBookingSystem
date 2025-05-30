package com.mayank.ShowBookingSystem.controller;

import com.mayank.ShowBookingSystem.model.Booking;
import com.mayank.ShowBookingSystem.model.Genre;
import com.mayank.ShowBookingSystem.model.SlotRequest;
import com.mayank.ShowBookingSystem.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizer")
public class OrganizerController {
    private final ShowService showService;

    @Autowired
    public OrganizerController(ShowService showService){
        this.showService = showService;
    }

    @PostMapping("/registerShow")
    public String registerShow(@RequestParam String name, @RequestParam Genre genre) {
        return showService.registerShow(name, genre);
    }

    @PostMapping("/onboardShowSlots")
    public String onboardShowSlots(@RequestParam String name, @RequestBody List<SlotRequest> slots) {
        return showService.onboardShowSlots(name, slots);
    }

    @GetMapping("/bookings")
    public List<Booking> bookings(@RequestParam String showName) {
        return showService.getShowBookings(showName);
    }
}

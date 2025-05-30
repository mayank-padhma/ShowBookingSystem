package com.mayank.ShowBookingSystem.controller;

import com.mayank.ShowBookingSystem.model.Genre;
import com.mayank.ShowBookingSystem.service.BookingService;
import com.mayank.ShowBookingSystem.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    private final ShowService showService;
    private final BookingService bookingService;

    @Autowired
    public PublicController(ShowService showService, BookingService bookingService){
        this.bookingService = bookingService;
        this.showService = showService;
    }

    @GetMapping("/trending")
    public String trending() {
        return bookingService.getTrendingShow();
    }

    @GetMapping("/showAvailByGenre")
    public List<String> showAvailByGenre(@RequestParam Genre genre) {
        return showService.getAvailableSlotsByGenre(genre);
    }
}

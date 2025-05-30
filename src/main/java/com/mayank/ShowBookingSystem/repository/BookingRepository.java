package com.mayank.ShowBookingSystem.repository;

import com.mayank.ShowBookingSystem.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class BookingRepository {
    private final Map<String, Booking> allBookings = new HashMap<>(); //
    private final Map<String, List<Booking>> userBookings = new HashMap<>();
    private final Map<String, Integer> bookingsCounter = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1000);

    public String getTrendingShows() {
        return bookingsCounter.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("No bookings yet");
    }

    public List<Booking> getUserBookings(String user) {
        return userBookings.getOrDefault(user, new ArrayList<>());
    }

    public String createBookingId() {
        return String.valueOf(idCounter.getAndIncrement());
    }

    public void addBooking(String id, Booking booking){
        allBookings.put(id, booking);
    }

    public boolean bookingExist(String bookingId) {
        return allBookings.containsKey(bookingId);
    }

    public Booking removeBooking(String bookingId) {
        return allBookings.remove(bookingId);
    }

    public void removeBookingFromUser(Booking booking) {
        userBookings.get(booking.getUser()).remove(booking);
    }

    public void updateTrendingMap(String showName, int bookings) {
        bookingsCounter.put(showName, bookings);
    }

    public int getBookingsCountByShowName(String showName) {
        return bookingsCounter.getOrDefault(showName, 0);
    }

    public void addBookingToUser(String user, Booking booking) {
        userBookings.computeIfAbsent(user, k -> new ArrayList<>()).add(booking);
    }
}

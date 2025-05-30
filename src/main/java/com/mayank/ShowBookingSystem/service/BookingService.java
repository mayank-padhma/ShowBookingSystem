package com.mayank.ShowBookingSystem.service;

import com.mayank.ShowBookingSystem.exception.ConflictException;
import com.mayank.ShowBookingSystem.exception.ResourceNotFoundException;
import com.mayank.ShowBookingSystem.model.Booking;
import com.mayank.ShowBookingSystem.model.BookingRequest;
import com.mayank.ShowBookingSystem.model.LiveShow;
import com.mayank.ShowBookingSystem.model.ShowSlot;
import com.mayank.ShowBookingSystem.repository.BookingRepository;
import com.mayank.ShowBookingSystem.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(ShowRepository showRepository, BookingRepository bookingRepository) {
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
    }

    public String book(String user, String showName, String time, int persons) {
        LiveShow show = showRepository.getLiveShowByName(showName);
        if (show == null || !show.getSlots().containsKey(time)) {
            throw new ResourceNotFoundException("Slot not found for show: " + showName + " at time: " + time);
        }

        for (Booking b : bookingRepository.getUserBookings(user)) {
            if (b.getTime().equals(time)) {
                throw new ConflictException("User already booked a slot at this time: " + time);
            }
        }

        ShowSlot slot = show.getSlots().get(time);
        if (slot.getBooked() + persons > slot.getCapacity()) {
            slot.getWaitlist().add(new BookingRequest(user, persons));
            return "Slot full. Added to waitlist.";
        }

        String id = bookingRepository.createBookingId();
        Booking booking = new Booking(id, user, showName, time, persons);

        int currentBookings = slot.getBooked();
        slot.setBooked(currentBookings + persons);
        slot.getBookings().put(id, booking);
        bookingRepository.addBooking(id, booking);
        bookingRepository.addBookingToUser(user, booking);
        bookingRepository.updateTrendingMap(showName, bookingRepository.getBookingsCountByShowName(showName) + persons);

        return "Booked. Booking id: " + id;
    }

    public String cancel(String bookingId) {
        if (!bookingRepository.bookingExist(bookingId)) {
            throw new ResourceNotFoundException("Invalid booking id: " + bookingId);
        }
        Booking booking = bookingRepository.removeBooking(bookingId);
        bookingRepository.removeBookingFromUser(booking);
        LiveShow show = showRepository.getLiveShowByName(booking.getShowName());
        ShowSlot slot = show.getSlots().get(booking.getTime());
        int bookedCount = slot.getBooked();
        slot.setBooked(bookedCount - booking.getPersons());
        slot.getBookings().remove(booking.getBookingId());

        bookingRepository.updateTrendingMap(booking.getShowName(), bookingRepository.getBookingsCountByShowName(booking.getShowName()) - booking.getPersons());

        while (!slot.getWaitlist().isEmpty()) {
            BookingRequest req = slot.getWaitlist().peek();
            if (req.getPersons() <= (slot.getCapacity() - slot.getBooked())) {
                slot.getWaitlist().poll();
                book(req.getUser(), booking.getShowName(), booking.getTime(), req.getPersons());
            } else break;
        }

        return "Booking Canceled";
    }

    public List<Booking> getUserBookings(String user) {
        return bookingRepository.getUserBookings(user);
    }

    public String getTrendingShow() {
        return bookingRepository.getTrendingShows();
    }
}

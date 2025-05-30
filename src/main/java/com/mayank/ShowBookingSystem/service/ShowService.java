package com.mayank.ShowBookingSystem.service;

import com.mayank.ShowBookingSystem.exception.ConflictException;
import com.mayank.ShowBookingSystem.exception.ResourceNotFoundException;
import com.mayank.ShowBookingSystem.model.*;
import com.mayank.ShowBookingSystem.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.mayank.ShowBookingSystem.utils.Utils.getSlotHour;
import static com.mayank.ShowBookingSystem.utils.Utils.getSlotMinute;

@Service
public class ShowService {
    private final ShowRepository repository;

    @Autowired
    public ShowService(ShowRepository repository) {
        this.repository = repository;
    }

    public String registerShow(String name, Genre genre) {
        if (repository.showExists(name)) throw new ConflictException("Show already exists");
        repository.addShow(name, genre);
        return name + " show is registered !!";
    }

    public String onboardShowSlots(String name, List<SlotRequest> requests) {
        if (!repository.showExists(name)) throw new ResourceNotFoundException("Show not found");
        LiveShow show = repository.getLiveShowByName(name);
        for (SlotRequest req : requests) {
            if (!isValidSlot(req.getStart(), req.getEnd())) throw new ConflictException("Sorry, show timings are of 1 hour only");
            if (!isValidTime(req.getStart(), req.getEnd())) throw new ConflictException("Sorry, show timings are between 9:00 - 23:00 only");
            if (show.getSlots().containsKey(req.getStart())) throw new ConflictException("Duplicate slot: " + req.getStart());
            ShowSlot slot = new ShowSlot();
            slot.setStart(req.getStart());
            slot.setEnd(req.getEnd());
            slot.setCapacity(req.getCapacity());
            show.getSlots().put(req.getStart(), slot);
        }
        return "Done!";
    }

    public List<String> getAvailableSlotsByGenre(Genre genre) {
        if (!repository.genreExists(genre)) {
            throw new ResourceNotFoundException("Genre not found: " + genre);
        }

        List<Map.Entry<String, ShowSlot>> availableSlots = new ArrayList<>();

        for (String showName : repository.getShowsByGenre(genre)) {
            LiveShow show = repository.getLiveShowByName(showName);
            for (ShowSlot slot : show.getSlots().values()) {
                if (slot.getBooked() < slot.getCapacity()) {
                    availableSlots.add(Map.entry(show.getName(), slot));
                }
            }
        }

        // Sort by start time
        availableSlots.sort(Comparator.comparing(e -> Integer.valueOf(e.getValue().getStart().split(":")[0])));

        // Format output
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, ShowSlot> entry : availableSlots) {
            ShowSlot slot = entry.getValue();
            result.add(entry.getKey() + ": (" + slot.getStart() + "-" + slot.getEnd() + ") " + (slot.getCapacity() - slot.getBooked()));
        }

        return result;
    }

    private boolean isValidSlot(String start, String end) {
        return getSlotHour(start) + 1 == getSlotHour(end) && getSlotMinute(end) == 0 && getSlotMinute(start) == 0;
    }

    private boolean isValidTime(String start, String end) {
        return getSlotHour(start) <= 20 && getSlotHour(start) >= 9;
    }

    public List<Booking> getShowBookings(String showName) {
        if(!repository.showExists(showName)) throw new ResourceNotFoundException("No live show exists with this name.");
        LiveShow show = repository.getLiveShowByName(showName);
        List<Booking> bookings = new ArrayList<>();
        for(ShowSlot slot: show.getSlots().values()){
            bookings.addAll(slot.getBookings().values());
        }
        return bookings;
    }
}

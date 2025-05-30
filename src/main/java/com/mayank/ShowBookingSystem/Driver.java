package com.mayank.ShowBookingSystem;

import com.mayank.ShowBookingSystem.model.Genre;
import com.mayank.ShowBookingSystem.model.SlotRequest;
import com.mayank.ShowBookingSystem.service.BookingService;
import com.mayank.ShowBookingSystem.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Driver implements CommandLineRunner {

    private final ShowService showService;
    private final BookingService bookingService;

    @Autowired
    public Driver(ShowService showService, BookingService bookingService) {
        this.showService = showService;
        this.bookingService = bookingService;
    }

    @Override
    public void run(String... args) throws Exception {
        runTests();
    }

    public void runTests(){
        System.out.println("i: registerShow: TMKOC -> Comedy");
        try {
            System.out.println("o: " + showService.registerShow("TMKOC", Genre.COMEDY));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i: onboardShowSlots: TMKOC 9:00-11:00");
        List<SlotRequest> invalidSlots = List.of(new SlotRequest("9:00", "11:00", 3));
        try {
            System.out.println("o: " + showService.onboardShowSlots("TMKOC", invalidSlots));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i: onboardShowSlots: TMKOC 9:00-10:00 3, 12:00-13:00 2, 15:00-16:00 5");
        List<SlotRequest> slots1 = List.of(
                new SlotRequest("9:00", "10:00", 3),
                new SlotRequest("12:00", "13:00", 2),
                new SlotRequest("15:00", "16:00", 5)
        );
        try {
            System.out.println("o: " + showService.onboardShowSlots("TMKOC", slots1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i: registerShow: The Sonu Nigam Live Event -> Singing");
        try {
            System.out.println("o: " + showService.registerShow("The Sonu Nigam Live Event", Genre.SINGING));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i: onboardShowSlots: The Sonu Nigam Live Event 10:00-11:00 3, 13:00-14:00 2, 17:00-18:00 1");
        List<SlotRequest> slots2 = List.of(
                new SlotRequest("10:00", "11:00", 3),
                new SlotRequest("13:00", "14:00", 2),
                new SlotRequest("17:00", "18:00", 1)
        );
        try {
            System.out.println("o: " + showService.onboardShowSlots("The Sonu Nigam Live Event", slots2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i: showAvailByGenre: Comedy");
        for (String s : showService.getAvailableSlotsByGenre(Genre.COMEDY)) System.out.println("o: " + s);

        System.out.println("i:  bookTicket: (UserA, TMKOC, 12:00, 2)");
        String id1 = bookingService.book("UserA", "TMKOC", "12:00", 2);
        try {
            System.out.println("o: " + id1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i: showAvailByGenre: Comedy");
        try {
            for (String s : showService.getAvailableSlotsByGenre(Genre.COMEDY)) System.out.println("o: " + s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String bookingId1 = id1.split(": ")[1];
        System.out.println("i: cancelBookingId: " + bookingId1);
        try {
            System.out.println("o: " + bookingService.cancel(bookingId1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i: showAvailByGenre: Comedy");
        try {
            for (String s : showService.getAvailableSlotsByGenre(Genre.COMEDY)) System.out.println("o: " + s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i:  bookTicket: (UserB, TMKOC, 12:00, 1)");
        String id2 = bookingService.book("UserB", "TMKOC", "12:00", 1);
        try {
            System.out.println("o: " + id2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i: registerShow: The Arijit Singh Live Event -> Singing");
        try {
            System.out.println("o: " + showService.registerShow("The Arijit Singh Live Event", Genre.SINGING));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i: onboardShowSlots: The Arijit Singh Live Event 11:00-12:00 3, 14:00-15:00 2");
        List<SlotRequest> slots3 = List.of(
                new SlotRequest("11:00", "12:00", 3),
                new SlotRequest("14:00", "15:00", 2)
        );
        try {
            System.out.println("o: " + showService.onboardShowSlots("The Arijit Singh Live Event", slots3));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("i: showAvailByGenre: Singing");
        for (String s : showService.getAvailableSlotsByGenre(Genre.SINGING)) System.out.println("o: " + s);

        System.out.println("i: Trending Live Show");
        try {
            System.out.println("o: " + bookingService.getTrendingShow());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

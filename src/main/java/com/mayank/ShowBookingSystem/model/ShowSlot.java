package com.mayank.ShowBookingSystem.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowSlot {
    String start;
    String end;
    int capacity;
    int booked = 0;
    Queue<BookingRequest> waitlist = new LinkedList<>();
    Map<String, Booking> bookings = new HashMap<>();
}

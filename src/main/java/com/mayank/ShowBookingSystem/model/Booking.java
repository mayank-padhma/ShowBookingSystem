package com.mayank.ShowBookingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    String bookingId;
    String user;
    String showName;
    String time;
    int persons;
}

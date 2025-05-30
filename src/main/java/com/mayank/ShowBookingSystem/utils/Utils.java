package com.mayank.ShowBookingSystem.utils;

public class Utils {
    static public int getSlotHour(String time) {
        return Integer.parseInt(time.split(":" )[0]);
    }
    static public int getSlotMinute(String time) {
        return Integer.parseInt(time.split(":" )[1]);
    }
}

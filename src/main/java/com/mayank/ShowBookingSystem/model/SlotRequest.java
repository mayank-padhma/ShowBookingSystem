package com.mayank.ShowBookingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotRequest {
    String start;
    String end;
    int capacity;
}

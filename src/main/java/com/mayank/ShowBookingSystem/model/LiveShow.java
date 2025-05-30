package com.mayank.ShowBookingSystem.model;

import java.util.Map;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveShow {
    String name;
    Genre genre;
    Map<String, ShowSlot> slots = new TreeMap<>();

    public LiveShow(String name, Genre genre) {
        this.name = name;
        this.genre = genre;
    }
}

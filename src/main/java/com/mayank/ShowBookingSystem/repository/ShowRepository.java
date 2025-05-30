package com.mayank.ShowBookingSystem.repository;

import com.mayank.ShowBookingSystem.model.Genre;
import com.mayank.ShowBookingSystem.model.LiveShow;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShowRepository {
    private final Map<String, LiveShow> shows = new HashMap<>(); // map(showName, liveShow)
    private final Map<Genre, List<String>> genreMap = new HashMap<>(); // map(genre, list of names of shows)

    public void addShow(String name, Genre genre){
        shows.put(name, new LiveShow(name, genre));
        genreMap.computeIfAbsent(genre, k -> new ArrayList<>()).add(name);
    }

    public boolean showExists(String name) {
        return shows.containsKey(name);
    }

    public LiveShow getLiveShowByName(String name) {
        return shows.get(name);
    }

    public List<String> getShowsByGenre(Genre genre) {
        return genreMap.getOrDefault(genre, new ArrayList<>());
    }

    public boolean genreExists(Genre genre) {
        return genreMap.containsKey(genre);
    }
}

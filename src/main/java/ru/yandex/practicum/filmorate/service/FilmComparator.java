package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class FilmComparator implements Comparator<Film> {
    @Override
    public int compare(Film o1, Film o2) {
        if (o1.getLikeCount() < o2.getLikeCount()) {
            return 1;
        }
        if (o1.getLikeCount() > o2.getLikeCount()) {
            return -1;
        } else {
            return 0;
        }
    }
}

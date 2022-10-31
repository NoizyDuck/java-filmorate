package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class InMemoryFilmService implements FilmService {
private int id = 0;
private final Map<Integer, Film> films = new HashMap<>();


    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
    film.setId(getNextId());
       films.put(film.getId(), film);
       return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        films.put(film.getId(), film);
        return film;
    }
    private int getNextId() {
        return ++id;
    }
}


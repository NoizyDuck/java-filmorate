//package ru.yandex.practicum.filmorate.storage;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ResponseStatusException;
//import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
//import ru.yandex.practicum.filmorate.model.Film;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//public class InMemoryFilmStorage implements FilmStorage {
//    private int id = 0;
//    private final Map<Integer, Film> films = new HashMap<>();
//
//
//    @Override
//    public List<Film> getAllFilms() {
//        return new ArrayList<>(films.values());
//    }
//
//    @Override
//    public Film createFilm(Film film) {
//        film.setId(incrementAndGet());
//        films.put(film.getId(), film);
//        return film;
//    }
//
//    @Override
//    public Film updateFilm(Film film) {
//        if (!films.containsKey(film.getId())) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        films.put(film.getId(), film);
//        return film;
//    }
//
//    @Override
//    public Film getFilmById(int id) {
//        Film film = films.get(id);
//        if(film == null){
//            throw new FilmNotFoundException("Film not found");
//        } else{
//        return film;
//    }
//    }
//
//    private int incrementAndGet() {
//        return ++id;
//    }
//
//
//}
//

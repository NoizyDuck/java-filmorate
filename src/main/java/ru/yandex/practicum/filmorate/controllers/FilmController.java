package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new IllegalArgumentException("Date should not be before 1985-12-28");
        }
        log.info("Film created" + film);
        return filmStorage.createFilm(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        log.info("Film updated" + film);
        return filmStorage.updateFilm(film);
    }

    @GetMapping
    public List<Film> getFilmList() {
        return filmStorage.getAllFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike (@PathVariable("id") int id, @PathVariable("userId") int userId) {
        filmService.addLike(id, userId);
    }

        @DeleteMapping("/{id}/like/{userId}")
        public void deleteLike (@PathVariable("id") int id, @PathVariable("userId") int userId){
            filmService.deleteLike(id, userId);
        }

        @GetMapping("/popular") //?count={count}"
    public List<Film> getTopFilms( @RequestParam(value = "count", defaultValue = "10", required = false) int count){
        if(count < 0){
            throw new IllegalArgumentException("count");
        }
        return filmService.getTopFilms(count);
    }

    @GetMapping("/{id}")
    public Film getFilmById (@PathVariable("id") int id){
       return filmStorage.getFilmById(id);
    }

}

package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film){
    if(film.getReleaseDate().isBefore(LocalDate.of( 1895,12,28))){
    throw new IllegalArgumentException("Date should not be before 1985-12-28");
    };
    log.info("Film created");
    return filmService.createFilm(film);
}

@PutMapping
    public Film put(@Valid @RequestBody Film film){
    log.info("Film updated");
    return filmService.updateFilm(film);
}

@GetMapping
    public List<Film> getFilmList(){
    return filmService.getAllFilms();
}
}

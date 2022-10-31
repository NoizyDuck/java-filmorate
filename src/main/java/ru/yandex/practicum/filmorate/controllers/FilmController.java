package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

private final List<Film> filmList = new ArrayList<>();

@PostMapping
    public Film create(@Valid @RequestBody Film film){
    if(film.getReleaseDate().isBefore(LocalDate.of( 1895,12,28))){
    throw new IllegalArgumentException("Date should not be before 1985-12-28");
    };
    log.info("Film created");
    filmList.add(film);
    return film;
}

@PutMapping
    public Film put(@Valid @RequestBody Film film){
    log.info("Film updated");
    filmList.add(film);
    return film;
}

@GetMapping
    public List<Film> getFilmList(){
    return filmList;
}
}

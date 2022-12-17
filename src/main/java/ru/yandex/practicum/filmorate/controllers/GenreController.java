package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/genres")
public class GenreController {

    private final MpaService mpaService;

    @GetMapping
    public Collection<Mpa> getAll(){
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    public Mpa getById (@PathVariable String id){
        return mpaService.getById(id);
    }
}

package ru.yandex.practicum.filmorate.storage;


import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.impl.FilmDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmStorageTest {
    private final FilmDbStorage filmStorage;

    private final Film film = new Film(1,
            "name",
            "description",
            LocalDate.now().minusYears(10),
            100,
            1,
            new Mpa(1, "name", "description"),
            new ArrayList<>(),
            new ArrayList<>());

    @Test
    public void addFilmTest() {
        filmStorage.createFilm(film);
        AssertionsForClassTypes.assertThat(film).extracting("id").isNotNull();
        AssertionsForClassTypes.assertThat(film).extracting("name").isNotNull();
    }

    @Test
    public void getFilmByIdTest() {
        filmStorage.createFilm(film);
        Film film = filmStorage.getFilmById(1);
        assertThat(film).hasFieldOrPropertyWithValue("id", 1);
    }

    @Test
    public void updateFilmTest() {
        Film filmToUpdate = filmStorage.createFilm(film);
        filmToUpdate.setName("updatedFilm");
        filmStorage.updateFilm(filmToUpdate);
        Film film = filmStorage.getFilmById(filmToUpdate.getId());
        assertThat(film).hasFieldOrPropertyWithValue("name", "updatedFilm");
    }


}
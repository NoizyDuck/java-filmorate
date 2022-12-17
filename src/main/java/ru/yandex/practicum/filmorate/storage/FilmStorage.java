package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
@Repository
public interface FilmStorage {
    List<Film> getAllFilms();

    Film createFilm(Film film);

    List<Film> getTopFilms(Integer count);

    Film updateFilm(Film film);

    Film getFilmById(int id);

    void addLike(int id, long id1);

    void deleteLike(int id, long id1);
}



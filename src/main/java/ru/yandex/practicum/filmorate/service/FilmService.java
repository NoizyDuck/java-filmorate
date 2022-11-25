package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FilmService {
    FilmComparator filmComparator = new FilmComparator();
    final FilmStorage filmStorage;
    final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new FilmNotFoundException("Film not found");
        }
        film.setLikeCount(film.getLikeCount() + 1);
        film.getLikes().add(userId);
    }

    public void deleteLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new FilmNotFoundException("Film not found");
        }
        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        film.setLikeCount(film.getLikeCount() - 1);
        film.getLikes().remove(userId);
    }

    public List<Film> getTopFilms(int count) {
        return filmStorage.getAllFilms()
                .stream()
                .sorted((f0, f1) -> filmComparator.compare(f0, f1))
                .limit(count)
                .collect(Collectors.toList());
    }
}

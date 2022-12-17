package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;


@Service
@Slf4j
public class FilmService {
   // FilmComparator filmComparator = new FilmComparator();
    final FilmStorage filmStorage;
    final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int filmId, long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        filmStorage.addLike(film.getId(), user.getId());
    }

//    public void addLike(int filmId, int userId) {
//        Film film = filmStorage.getFilmById(filmId);
//        if (film == null) {
//            throw new ObjectNotFoundException("Film not found");
//        }
//        film.setLikeCount(film.getLikeCount() + 1);
//        film.getLikes().add(userId);
//    }
public void deleteLike(int filmId, int userId) {
    Film film = filmStorage.getFilmById(filmId);
    User user = userStorage.getUserById(userId);
    filmStorage.deleteLike(film.getId(), user.getId());
}
//    public void deleteLike(int filmId, int userId) {
//        Film film = filmStorage.getFilmById(filmId);
//        if (film == null) {
//            throw new ObjectNotFoundException("Film not found");
//        }
//        User user = userStorage.getUserById(userId);
//        if (user == null) {
//            throw new ObjectNotFoundException("User not found");
//        }
//        film.setLikeCount(film.getLikeCount() - 1);
//        film.getLikes().remove(userId);
//    }
    public List<Film> getTopFilms(int count) {
        return filmStorage.getTopFilms(count);
    }
//    public List<Film> getTopFilms(int count) {
//        return filmStorage.getAllFilms()
//                .stream()
//                .sorted((f0, f1) -> filmComparator.compare(f0, f1))
//                .limit(count)
//                .collect(Collectors.toList());
//    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }
}

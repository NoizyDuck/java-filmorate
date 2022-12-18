package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public Collection<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

    public Collection<Genre> getFilmGenres(int filmId) {
        return genreStorage.getGenresByFilmId(filmId);
    }

    public Genre getGenreById(Integer id) {
        return genreStorage.getGenreById(id);
    }

    public void addFilmGenres(int filmId, Collection<Genre> genres) {
        genreStorage.addFilmGenres(filmId, genres);
    }

    public void deleteFilmGenres(int filmId) {
       genreStorage.deleteFilmGenres(filmId);
    }




}

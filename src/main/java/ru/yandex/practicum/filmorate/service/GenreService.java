package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreStorage;

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

    public Genre getGenreById(String strId) {
        int id = parseId(strId);
        return genreStorage.getGenreById(id);
    }
    private Integer parseId(final String strId) {
        try {
            return Integer.valueOf(strId);
        } catch (NumberFormatException exception) {
            return Integer.MIN_VALUE;
        }
    }

    public void addFilmGenres(int filmId, Collection<Genre> genres) {
        genreStorage.addFilmGenres(filmId, genres);
    }

    public void deleteFilmGenres(int filmId) {
       genreStorage.deleteFilmGenres(filmId);
    }




}

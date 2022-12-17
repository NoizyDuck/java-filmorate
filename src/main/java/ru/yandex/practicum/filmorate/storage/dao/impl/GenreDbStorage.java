package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
@Component
@RequiredArgsConstructor

public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Genre> getAllGenres() {
        return null;
    }

    @Override
    public Collection<Genre> getGenresByFilmId(int filmId) {
        String sqlQuery = "SELECT GENRES.GENRE_ID, GENRES.GENRE_NAME FROM GENRES " +
                "JOIN FILM_GENRE ON GENRES.GENRE_ID = FILM_GENRE.GENRE_ID " +
                "WHERE FILM_ID = ?";
        return jdbcTemplate.query(sqlQuery, this::makeGenre, filmId);
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"), rs.getString("GENRE_NAME"));
    }

    @Override
    public Genre getGenreById(int genreId) {
        String sqlQuery = "SELECT * FROM GENRES WHERE GENRE_ID = ?";
        Genre genre;
        try {
            genre = jdbcTemplate.queryForObject(sqlQuery, this::makeGenre, genreId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Genre id " + genreId + " not found"));
        }
        return genre;
    }

    @Override
    public void addFilmGenres(int filmId, Collection<Genre> genres) {
        for (Genre genre : genres) {
            String sqlQuery = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";
            jdbcTemplate.update(sqlQuery, filmId, genre.getId());
        }
    }

    @Override
    public void deleteFilmGenres(int filmId) {
        String sqlQuery = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

}

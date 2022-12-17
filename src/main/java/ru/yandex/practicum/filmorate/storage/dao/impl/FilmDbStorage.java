package ru.yandex.practicum.filmorate.storage.dao.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreService genreService;

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT * FROM FILMS " +
                "INNER JOIN RATING_MPA ON FILMS.RATING_ID = RATING_MPA.RATING_ID ";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "INSERT INTO FILMS " +
                "(FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, RATING_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, film.getName());
            prepareStatement.setString(2, film.getDescription());
            prepareStatement.setDate(3, Date.valueOf(film.getReleaseDate()));
            prepareStatement.setLong(4, film.getDuration());
            prepareStatement.setInt(5, film.getRate());
            prepareStatement.setInt(6, film.getMpa().getId());
            return prepareStatement;
        }, keyHolder);
        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        film.setId(id);

        if (!film.getGenres().isEmpty()) {
            genreService.addFilmGenres(film.getId(), film.getGenres());
        }
        if (film.getLikes() != null) {
            for (Integer userId : film.getLikes()) {
                addLike(film.getId(), userId);
            }
        }
        return getFilmById(id);
    }

    private Film makeFilm(ResultSet resultSet) throws SQLException {
        int filmId = resultSet.getInt("FILM_ID");
        return new Film(
                filmId,
                resultSet.getString("FILMS.FILM_NAME"),
                resultSet.getString("FILMS.DESCRIPTION"),
                Objects.requireNonNull(resultSet.getDate("FILMS.RELEASE_DATE")).toLocalDate(),
                resultSet.getInt("FILMS.DURATION"),
                resultSet.getInt("FILMS.RATE"),
                new Mpa(resultSet.getInt("RATING_ID"),
                        resultSet.getString("MPA_NAME"),
                        resultSet.getString("DESCRIPTION")),
                (List<Genre>) genreService.getFilmGenres(filmId),
                getFilmLikes(filmId)
        );
    }

    private List getFilmLikes(int filmId) {
        String sqlQuery = "SELECT USER_ID FROM LIKES WHERE FILM_ID = ?";
        return jdbcTemplate.queryForList(sqlQuery, Integer.class, filmId);
    }
    @Override
    public List<Film> getTopFilms(Integer count) {
        String sqlQuery = "SELECT COUNT(L.LIKE_ID) AS Like_count" +
                ",FILMS.FILM_ID, FILMS.FILM_NAME, FILMS.DESCRIPTION, " +
                "FILMS.RELEASE_DATE, FILMS.DURATION, FILMS.RATE, R.RATING_ID, R.MPA_NAME, R.DESCRIPTION FROM FILMS " +
                "LEFT JOIN LIKES AS L ON L.FILM_ID = FILMS.FILM_ID " +
                "INNER JOIN RATING_MPA AS R ON R.RATING_ID = FILMS.RATING_ID " +
                "GROUP BY FILMS.FILM_ID " +
                "ORDER BY Like_count DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs), count);
    }



    @Override
    public Film updateFilm(Film film) {
        String sqlQueryDel = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQueryDel, film.getId());

        String sqlQuery = "UPDATE FILMS " +
                "SET FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATE = ? , RATING_ID = ? " +
                "WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId(),
                film.getId());

        genreService.deleteFilmGenres(film.getId());
        if (!film.getGenres().isEmpty()) {
            genreService.addFilmGenres(film.getId(), film.getGenres());
        }

        if (film.getLikes() != null) {
            for (Integer userId : film.getLikes()) {
                addLike(film.getId(), userId);
            }
        }
        return getFilmById(film.getId());
    }

    public void addLike(int filmId, long userId) {
        String sqlQuery = "SELECT * FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        SqlRowSet existLike = jdbcTemplate.queryForRowSet(sqlQuery, userId, filmId);
        if (!existLike.next()) {
            String setLike = "INSERT INTO LIKES (USER_ID, FILM_ID) VALUES  (?, ?) ";
            jdbcTemplate.update(setLike, userId, filmId);
        }
    }

    @Override
    public void deleteLike(int filmId, long userId) {
        String deleteLike = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(deleteLike, filmId, userId);
    }

    @Override
    public Film getFilmById(int filmId) {
        String sqlQuery = "SELECT * FROM FILMS " +
                "INNER JOIN RATING_MPA ON FILMS.RATING_ID = RATING_MPA.RATING_ID " +
                "WHERE FILM_ID = ?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeFilm(rs), filmId);
        } catch (EmptyResultDataAccessException exception) {
            throw new ObjectNotFoundException(String.format("Film " + filmId + " not found"));
        }
        return film;
    }
}

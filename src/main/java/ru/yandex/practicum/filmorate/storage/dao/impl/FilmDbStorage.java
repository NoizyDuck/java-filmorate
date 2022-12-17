package ru.yandex.practicum.filmorate.storage.dao.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreService genreService;

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT * FROM FILMS";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "insert into FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, RATING_ID) values (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            final LocalDate releaseDate = film.getReleaseDate();
            if (releaseDate == null) {
                stmt.setNull(3, Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(releaseDate));
            }
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getRate());
            stmt.setInt(6, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return film;
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        int filmId = rs.getInt("FILM_ID");
        return new Film(
                filmId,
                rs.getString("FILM_NAME"),
                rs.getString("DESCRIPTION"),
                Objects.requireNonNull(rs.getDate("RELEASE_DATE")).toLocalDate(),
                rs.getInt("DURATION"),
                rs.getInt("RATE"),
                rs.getInt("RATING_ID"),
                new Mpa( rs.getInt("RATING_MPA.RATING_ID"),
                        rs.getString("RATING_MPA.MPA_NAME"),
                        rs.getString("RATING_MPA.DESCRIPTION")),
                (List<Genre>) genreService.getFilmGenres(filmId),
        getFilmLikes(filmId));

    }

    private Object getFilmLikes(int filmId) {
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
        jdbcTemplate.update(sqlQuery,film.getName(),
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

    private void addLike(int filmId, Integer userId) {
        String sqlQuery = "SELECT * FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        SqlRowSet existLike = jdbcTemplate.queryForRowSet(sqlQuery, userId, filmId);
        if (!existLike.next()) {
            String setLike = "INSERT INTO LIKES (USER_ID, FILM_ID) VALUES  (?, ?) ";
            jdbcTemplate.update(setLike, userId, filmId);
        }
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

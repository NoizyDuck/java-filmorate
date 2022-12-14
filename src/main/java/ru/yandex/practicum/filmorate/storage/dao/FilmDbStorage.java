package ru.yandex.practicum.filmorate.storage.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component("FilmDbStorage")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT * FROM FILMS";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs));
    }

    private User makeFilm(ResultSet rs) throws SQLException {
        int filmId = rs.getInt("FILM_ID");
        return new Film(
                filmId,
                rs.getString("FILM_NAME"),
                rs.getString("DESCRIPTION"),
                Objects.requireNonNull(rs.getDate("RELEASE_DATE")).toLocalDate(),
                rs.getInt("DURATION"),
                rs.getInt("RATING"),
                rs.getInt("RATING_ID"));

    }

}

    @Override
    public Film createFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public Film getFilmById(int id) {
        return null;
    }
}

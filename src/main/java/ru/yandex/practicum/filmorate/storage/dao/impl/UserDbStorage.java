package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<User> getAllUsers() {
        String sqlQuery = "SELECT * FROM USERS";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        boolean friendAccepted;
        String sqlGetReversFriend = "SELECT * FROM FRIENDSHIP " +
                "WHERE USER_ID = ? AND FRIEND_ID = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlGetReversFriend, friendId, userId);
        friendAccepted = rs.next();
        String sqlSetFriend = "INSERT INTO FRIENDSHIP (USER_ID, FRIEND_ID, STATUS) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlSetFriend, userId, friendId, friendAccepted);
        if (friendAccepted) {
            String sqlSetStatus = "UPDATE FRIENDSHIP SET STATUS = true " +
                    "WHERE USER_ID = ? AND FRIEND_ID = ?";
            jdbcTemplate.update(sqlSetStatus, friendId, userId);
        }
    }

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) values (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            final LocalDate birthday = user.getBirthday();
            if (birthday == null) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4, Date.valueOf(birthday));
            }
            return stmt;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        final String sqlQuery = "UPDATE USERS SET EMAIL = ?, LOGIN = ?, USER_NAME = ?, BIRTHDAY = ? " +
                "WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return getUserById(user.getId());
    }

    @Override
    public User getUserById(long id) {
//        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT USER_ID, EMAIL, LOGIN, BIRTHDAY, USER_NAME FROM USERS WHERE USER_ID = ?", id);
//        if (userRows.next()) {
//            User user = new User(
//                    userRows.getInt("USER_ID"),
//                    userRows.getString("EMAIL"),
//                    userRows.getString("LOGIN"),
//                    userRows.getDate("BIRTHDAY"),
//                    userRows.getString("USER_NAME"));
//            return user;
//        } else {
//            return null;
//        }
//    }
        final String sqlQuery = "SELECT USER_ID, EMAIL, LOGIN, BIRTHDAY, USER_NAME FROM USERS WHERE USER_ID = ?";
        User user;
        try {
            user = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeUser(rs), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("User " + id + " not found");
        }
        return user;
    }
//@Override
//public User getUserById(long id) {
//    final String sqlQuery = "SELECT USER_ID, EMAIL, LOGIN, BIRTHDAY, USER_NAME FROM USERS WHERE USER_ID = ?";
//    return jdbcTemplate.queryForObject(sqlQuery,(rs, rowNum) -> makeUser(rs), id);
//}


    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
    }

    private User makeUser(ResultSet rs) throws SQLException {
        long userId = rs.getLong("USER_ID");
        return new User(
                userId,
                rs.getString("EMAIL"),
                rs.getString("LOGIN"),
                rs.getString("USER_NAME"),
                Objects.requireNonNull(rs.getDate("BIRTHDAY")).toLocalDate(),
                getUserFriends(userId));
    }

    private List<Integer> getUserFriends(long userId) {
        String sqlGetFriends = "SELECT FRIEND_ID FROM FRIENDSHIP WHERE USER_ID = ?";
        return jdbcTemplate.queryForList(sqlGetFriends, Integer.class, userId);
    }


}

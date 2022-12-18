package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.impl.UserDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class UserStorageTest {
    private final UserDbStorage userStorage;

    private final User user1 = new User(1,
            "user1@mail.ru",
            "userLogin1",
            "userName1",
            LocalDate.of(1984, 4, 4),
            new ArrayList<>());
    private final User user2 = new User(2,
            "user2@mail.ru",
            "userLogin2",
            "userName2",
            LocalDate.of(1985, 5, 5),
            new ArrayList<>());
    private final User user3 = new User(3,
            "user3@mail.ru",
            "userLogin3",
            "userName3",
            LocalDate.of(1986, 6, 6),
            new ArrayList<>());
    private final User user4 = new User(4,
            "user4@mail.ru",
            "userLogin4",
            "userName4",
            LocalDate.of(1987, 7, 7),
            new ArrayList<>());

    @Test
    public void createUserTest() {
        userStorage.createUser(user1);
        AssertionsForClassTypes.assertThat(user1).extracting("id").isNotNull();
        AssertionsForClassTypes.assertThat(user1).extracting("name").isNotNull();
    }

    @Test
    public void getUserById() {
        userStorage.createUser(user2);
        User expectedUser = user2;
        assertEquals(user2, expectedUser);
    }

    @Test
    public void getAllUsersTest() {
        userStorage.createUser(user3);
        userStorage.createUser(user4);
        Collection<User> dbUsers = userStorage.getAllUsers();
        assertEquals(2, dbUsers.size());
    }

}
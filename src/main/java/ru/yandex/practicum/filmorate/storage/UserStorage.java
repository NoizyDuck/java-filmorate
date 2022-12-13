package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {

    User createUser(User user);
    User getUserById(long userId);
    User updateUser(User user);

    void deleteUser(User user);
    User deleteUserById (Long id);


    List<User> getAllUsers();



}

package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User createUser(User user);

    User getUserById(long userId);

    User updateUser(User user);

    void deleteUser(User user);

    User deleteUserById(long id);


    Collection<User> getAllUsers();

    void addFriend (Integer userId, Integer friendId);
    void deleteFriend (Integer userId, Integer friendId);



}

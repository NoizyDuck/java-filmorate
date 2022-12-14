package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    final UserStorage userStorage;
    @Autowired
    public UserService(UserStorage userStorage){
        this.userStorage = userStorage;
    }

    public User createUser(User user){
        return userStorage.createUser(user);
    }

    public void addFriend (int userId, Integer friendId){
        userStorage.addFriend(userId, friendId);
}
    public void removeFriend(int userId, Integer friendId){
        userStorage.deleteFriend(userId,friendId);
    }


public List<User> getUserFriendsList(int userId){
    User user = validateAndGetUser(userId);
    return user.getFriends().stream().map(userStorage::getUserById).collect(Collectors.toList());
}


    public List<User> getCommonFriendsList(int userId, int otherId) {
        User user = validateAndGetUser(userId);
        User otherUser = userStorage.getUserById(otherId);
        return user.getFriends().stream()
                .filter(friendId -> otherUser.getFriends().contains(friendId))
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    private User validateAndGetUser(int userId) {
        User user = userStorage.getUserById(userId);
        if(user == null){
            throw new ObjectNotFoundException("User not found");
        }
        return user;
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }
}

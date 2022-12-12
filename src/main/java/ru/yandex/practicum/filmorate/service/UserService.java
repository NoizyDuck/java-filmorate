package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    final ru.yandex.practicum.filmorate.storage.UserStorage userStorage;
    @Autowired
    public UserService(ru.yandex.practicum.filmorate.storage.UserStorage userStorage){
        this.userStorage = userStorage;
    }

    public void addFriend (int userId, Integer friendId){
        User user = validateAndGetUser(userId);
        User friendUser = validateAndGetUser(friendId);
        user.getFriends().add(friendId);
       friendUser.getFriends().add(userId);
}
    public void removeFriend(int userId, Integer friendId){
        User user = validateAndGetUser(userId);
        user.getFriends().remove(friendId);
        User friendUser = validateAndGetUser(friendId);
        friendUser.getFriends().remove(friendId);
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

}

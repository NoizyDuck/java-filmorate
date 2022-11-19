package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
       User user = userStorage.getUserById(userId);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
       User friendUser = userStorage.getUserById(friendId);
        if(friendUser == null){
            throw new UserNotFoundException("User not found");
        }
       user.getFriends().add(friendId);
       friendUser.getFriends().add(userId);
}
    public void removeFriend(int userId, Integer friendId){
        User user = userStorage.getUserById(userId);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        user.getFriends().remove(friendId);
        User friendUser = userStorage.getUserById(friendId);
        if(friendUser == null){
            throw new UserNotFoundException("User not found");
        }
        friendUser.getFriends().remove(friendId);
    }


public List<User> getUserFriendsList(int userId){
        User user = userStorage.getUserById(userId);
    if(user == null){
        throw new UserNotFoundException("User not found");
    }
    return user.getFriends().stream().map(userStorage::getUserById).collect(Collectors.toList());
}


    public List<User> getCommonFriendsList(int userId, int otherId) {
        User user = userStorage.getUserById(userId);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        User otherUser = userStorage.getUserById(otherId);
        return user.getFriends().stream()
                .filter(friendId -> otherUser.getFriends().contains(friendId))
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }
}

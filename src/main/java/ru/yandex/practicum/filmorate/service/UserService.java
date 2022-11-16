package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;
@Service
public class UserService {

    final UserStorage userStorage;
    @Autowired
    public UserService (UserStorage userStorage){
        this.userStorage = userStorage;
    }

    public void addFriend (int userId, Integer friendId){
       User user = userStorage.getUserById(userId);
       User friendUser = userStorage.getUserById(friendId);
       user.getFriends().add(friendId);
       friendUser.getFriends().add(userId);
}
    public void removeFriend(int userId, Integer friendId){
        User user = userStorage.getUserById(userId);
        user.getFriends().remove(friendId);
    }


public Set<Integer> showAllFriends(int userId){
    User user = userStorage.getUserById(userId);
   return user.getFriends();
}


}

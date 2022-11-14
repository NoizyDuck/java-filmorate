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

    public void addFriend (int id, Integer friendId){
       User user = userStorage.
}

public void deleteFriend(Long id){
    if (!friends.contains(id)){
        friends.remove(id);
        System.out.println("Friend " + id + " deleted from friends list :'(");
    } else {
        System.out.println("No such friend on friends list");
    }
}
public Set<Long> showAllFriends(){
    return this.friends;
}


}

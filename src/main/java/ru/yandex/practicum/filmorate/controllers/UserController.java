package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        log.info("User added" + user);
        return userService.createUser(user);
    }

    @GetMapping
    public Collection<User> findAll() {
        return userService.getAllUsers();
    }

    @PutMapping
    public User put(@RequestBody @Valid User user) {
        log.info("User updated" + user);
        return userService.updateUser(user);
    }

    @PutMapping ("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") int id, @PathVariable("friendId") int friendId){
        userService.addFriend(id, friendId);
    }

    @DeleteMapping ("{id}/friends/{friendId}")
    public void deleteFriend (@PathVariable("id") int userId, @PathVariable("friendId") int friendId){
        userService.removeFriend(userId, friendId);
    }
    @GetMapping ("/{id}/friends")
    public List<User> findAllFriends (@PathVariable("id") int userId){
        return userService.getUserFriendsList(userId);
    }
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriendList(@PathVariable("id") int userId, @PathVariable("otherId") int otherId){
        return userService.getCommonFriendsList(userId, otherId);
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id){
        return userService.getUserById(id);
    }
//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable("id") int id){
//      return userService.getUserById(id);
//    }
}

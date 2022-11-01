package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
}

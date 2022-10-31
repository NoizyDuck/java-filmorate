package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
private final Map<String, User> userMap = new HashMap<>();

@PostMapping
    public User create (@RequestBody @Valid User user){
    if(user.getName() == null){
        user.setName(user.getLogin());
    }
    log.info("User added");
userMap.put(user.getEmail(), user);
return user;
}

@GetMapping
public Collection<User>findAll(){
return userMap.values();
}

@PutMapping
    public User put (@RequestBody @Valid User user){
    log.info("User updated");
userMap.put(user.getEmail(), user);
return user;
}
}

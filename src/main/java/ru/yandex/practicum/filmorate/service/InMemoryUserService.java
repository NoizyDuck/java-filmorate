package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryUserService implements UserService {
    private int id = 0;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }


    @Override
    public User createUser(User user) {
        String name = user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName();
        user.setName(name);
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        users.put(user.getId(), user);
        return user;
    }


    private int getNextId() {
        return ++id;
    }
}

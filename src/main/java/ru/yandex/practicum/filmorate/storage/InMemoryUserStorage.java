package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
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
        user.setId(incrementAndGet());
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

    @Override
    public User getUserById(int userId) {
     //   User user = users.get(userId);
        if (!users.containsKey(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return users.get(userId);
    }


    private int incrementAndGet() {
        return ++id;
    }
}

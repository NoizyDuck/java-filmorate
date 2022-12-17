package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaStorage;

import java.util.Collection;
@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public Collection<Mpa> getAll() {
        return mpaStorage.getAll();
    }

    public Mpa getById(String strId) {
        int id = parseId(strId);
        return mpaStorage.getById(id);
    }

    private Integer parseId(final String strId) {
        try {
            return Integer.valueOf(strId);
        } catch (NumberFormatException exception) {
            return Integer.MIN_VALUE;
        }
    }
}

package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaStorageTest {
    private final MpaStorage mpaStorage;

    @Test
    public void getAllTest() {
        Collection<Mpa> mpa = mpaStorage.getAll();
        Assertions.assertThat(mpa)
                .extracting(Mpa::getName)
                .containsAll(Arrays.asList("G", "PG", "PG-13", "R", "NC-17"));
    }

    @Test
    public void getByIdTest() {
        Optional<Mpa> mpaOptional = Optional.ofNullable(mpaStorage.getById(1));
        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasFieldOrPropertyWithValue("id", 1)
                );
    }
}
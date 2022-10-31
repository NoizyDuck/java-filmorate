package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
@Data
public class Film {
    int id;
    @NotEmpty //(message = "Film name should be not empty")
    String name;
    @Size(max = 200)
    String description;
    LocalDate releaseDate;
    @Positive //(message = "Duration should be positive")
    int duration;
}

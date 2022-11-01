package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {
    int id;
    @NotBlank //(message = "Film name should be not empty")
    String name;
    @NotBlank
    @Size(max = 200)
    String description;
    @DateTimeFormat(pattern = "YYYY-mm-dd")
    @NotNull
    LocalDate releaseDate;
    @Positive //(message = "Duration should be positive")
    int duration;
}

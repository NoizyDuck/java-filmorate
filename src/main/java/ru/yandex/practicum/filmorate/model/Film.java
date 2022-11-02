package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {
    int id;
    @NotBlank (message = "Film name should be not empty")
    String name;
    @NotBlank (message = "Description should be not empty")
    @Size(max = 200, message = "Description should be not longer then 200 charts")
    String description;
    @DateTimeFormat(pattern = "YYYY-mm-dd")
    @NotNull (message = "Release date should be not empty")
    LocalDate releaseDate;
    @Positive (message = "Duration should be positive")
    int duration;
}

package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    int id;
    @NotBlank(message = "Film name should be not empty")
    String name;
    @NotBlank(message = "Description should be not empty")
    @Size(max = 200, message = "Description should be not longer then 200 charts")
    String description;
    @DateTimeFormat(pattern = "YYYY-mm-dd")
    @NotNull(message = "Release date should be not empty")
    LocalDate releaseDate;
    @Positive(message = "Duration should be positive")
    int duration;
    Set<Integer> likes =new HashSet<>();
    int likeCount = 0;
}

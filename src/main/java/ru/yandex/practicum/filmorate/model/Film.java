package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor()
@NoArgsConstructor
@Valid
public class Film {
    private int id;
    @NotBlank(message = "Film name should be not empty")
    private String name;
    @NotBlank(message = "Description should be not empty")
    @Size(max = 200, message = "Description should be not longer then 200 charts")
    private String description;
    @DateTimeFormat(pattern = "YYYY-mm-dd")
    @NotNull(message = "Release date should be not empty")
    private LocalDate releaseDate;
    @Positive(message = "Duration should be positive")
    private int duration;
    private int rate;
    @NotNull
    private Mpa mpa;
    private List<Genre> genres = new ArrayList<>();
    private List<Integer> likes = new ArrayList<>();

    int likeCount = 0;
}

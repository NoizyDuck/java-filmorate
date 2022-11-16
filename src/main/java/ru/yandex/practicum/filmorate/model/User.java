package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
public class User {
    int userId;
    @Email(message = "email should be valid")
    String email;
    @NotBlank(message = "login should be not empty or contains empty spaces")
    String login;
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 characters")
    String name;
    @NotNull
    @PastOrPresent(message = "birthday date should be not from future")
    @DateTimeFormat(pattern = "YYYY-mm-dd")
    LocalDate birthday;
    Set<Integer> friends;

}

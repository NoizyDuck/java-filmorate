package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    int id;
    @Email //(message = "email should be valid")
    String email;
    @NotBlank //(message = "login should be not empty or contains empty spaces")
    String login;
    //  @NotEmpty (message = "Name should be not empty")
    //  @Size(min = 2, max = 20) //, message = "Name should be between 2 and 20 characters")
    String name;
    @NotBlank
    @PastOrPresent //(message = "birthday date should be not from future")
    @DateTimeFormat(pattern = "YYYY-mm-dd")
    LocalDate birthday;

}

package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class User {

    long id;
    @Email(message = "email should be valid")
    String email;
    @NotNull
    @NotBlank(message = "login should be not empty or contains empty spaces")
    String login;
    String name;
    @NotNull
    @PastOrPresent(message = "birthday date should be not from future")
    @DateTimeFormat(pattern = "YYYY-mm-dd")
    LocalDate birthday;
    List<Integer> friends = new ArrayList<>();



}

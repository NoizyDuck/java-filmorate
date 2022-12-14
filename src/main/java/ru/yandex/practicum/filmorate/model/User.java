package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor()
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
    Set<Integer> friends = new HashSet<>();
   // String friendshipStatus;


    public User(int userId, String email, String login, String user_name, LocalDate birthday, List<Integer> userFriends) {
    }
}

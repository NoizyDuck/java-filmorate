package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "id")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private int id;
    private String name;
}

package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmService {
    //TODO дописать
private Set<Long> likes;

    public void addLike (Long id){
        likes.add(id);
    }
    public void deleteLike(Long id){

    }
//    public Set<Long> showTopTenFilms(){
//        return posts.stream()
//                .filter(p -> email.equals(p.getAuthor()))
//                .sorted((p0, p1) -> compare(p0, p1, sort))
//                .limit(size)
//                .collect(Collectors.toList());
//    }
}

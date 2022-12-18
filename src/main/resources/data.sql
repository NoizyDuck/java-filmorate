
ALTER TABLE USERS
    ALTER COLUMN USER_ID RESTART WITH 1;
ALTER TABLE FILMS
    ALTER COLUMN FILM_ID RESTART WITH 1;
ALTER TABLE FRIENDSHIP
    ALTER COLUMN FRIENDSHIP_ID RESTART WITH 1;
ALTER TABLE FILM_GENRE
    ALTER COLUMN FILM_GENRE_ID RESTART WITH 1;
ALTER TABLE LIKES
    ALTER COLUMN LIKE_ID RESTART WITH 1;

MERGE INTO RATING_MPA KEY (RATING_ID)
    VALUES (1, 'G', 'у фильма нет возрастных ограничений'),
           (2, 'PG', 'детям рекомендуется смотреть фильм с родителями'),
           (3, 'PG-13', 'детям до 13 лет просмотр не желателен'),
           (4, 'R', 'лицам до 17 лет обязательно присутствие взрослого'),
           (5, 'NC-17', 'лицам  до 18 лет просмотр запрещен');

MERGE INTO GENRES KEY (GENRE_ID)
    VALUES (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Триллер'),
           (5, 'Документальный'),
           (6, 'Боевик');
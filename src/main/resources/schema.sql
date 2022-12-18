CREATE TABLE IF NOT EXISTS FILMS
(
    FILM_ID      INT          NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    FILM_NAME    VARCHAR(100) NOT NULL,
    DESCRIPTION  VARCHAR(250) NOT NULL,
    RELEASE_DATE DATE         NOT NULL,
    DURATION     INT          NOT NULL,
    RATE         INT,
    RATING_ID    INT          NOT NULL,
    CONSTRAINT pk_Film PRIMARY KEY (FILM_ID)
);

CREATE TABLE IF NOT EXISTS LIKES
(
    LIKE_ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    USER_ID INT NOT NULL,
    FILM_ID INT NOT NULL,
    CONSTRAINT pk_Like PRIMARY KEY (LIKE_ID)
);

CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID   INT         NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    EMAIL     VARCHAR(100) NOT NULL,
    LOGIN     VARCHAR(100) NOT NULL,
    USER_NAME VARCHAR(50) NOT NULL,
    BIRTHDAY  DATE        NOT NULL,
    CONSTRAINT pk_User PRIMARY KEY (USER_ID),
    CONSTRAINT uc_User_Email UNIQUE (EMAIL)
);

CREATE TABLE IF NOT EXISTS FRIENDSHIP
(
    FRIENDSHIP_ID INT  NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    USER_ID       INT  NOT NULL,
    FRIEND_ID     INT  NOT NULL,
    STATUS        BOOL NOT NULL,
    CONSTRAINT pk_Friendship PRIMARY KEY (FRIENDSHIP_ID)
);

CREATE TABLE IF NOT EXISTS FILM_GENRE
(
    FILM_GENRE_ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    FILM_ID       INT NOT NULL,
    GENRE_ID      INT NOT NULL,
    CONSTRAINT pk_Film_Genre PRIMARY KEY (FILM_GENRE_ID)
);

CREATE TABLE IF NOT EXISTS GENRES
(
    GENRE_ID   INT          NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    GENRE_NAME VARCHAR(55) NOT NULL,
    CONSTRAINT pk_Genre PRIMARY KEY (GENRE_ID),
    CONSTRAINT uc_Genre_Name UNIQUE (GENRE_NAME)
);

CREATE TABLE IF NOT EXISTS RATING_MPA
(
    RATING_ID   INT          NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    MPA_NAME    VARCHAR(25)  NOT NULL,
    DESCRIPTION VARCHAR(255) NOT NULL,
    CONSTRAINT pk_RatingMPA PRIMARY KEY (RATING_ID),
    CONSTRAINT uc_RatingMPA_Name UNIQUE (MPA_NAME)
);

ALTER TABLE FILMS
    ADD CONSTRAINT IF NOT EXISTS fk_Film_RatingID FOREIGN KEY (RATING_ID)
        REFERENCES RATING_MPA (RATING_ID) ON DELETE RESTRICT;

ALTER TABLE LIKES
    ADD CONSTRAINT IF NOT EXISTS fk_Like_FilmID FOREIGN KEY (FILM_ID)
        REFERENCES FILMS (FILM_ID) ON DELETE CASCADE;

ALTER TABLE LIKES
    ADD CONSTRAINT IF NOT EXISTS fk_Like_UserID FOREIGN KEY (USER_ID)
        REFERENCES USERS (USER_ID);

ALTER TABLE FRIENDSHIP
    ADD CONSTRAINT IF NOT EXISTS fk_Friendship_UserID FOREIGN KEY (USER_ID)
        REFERENCES USERS (USER_ID);

ALTER TABLE FRIENDSHIP
    ADD CONSTRAINT IF NOT EXISTS fk_Friendship_FriendID FOREIGN KEY (FRIEND_ID)
        REFERENCES USERS (USER_ID);

ALTER TABLE FILM_GENRE
    ADD CONSTRAINT IF NOT EXISTS fk_Film_Genre_FilmID FOREIGN KEY (FILM_ID)
        REFERENCES FILMS (FILM_ID) ON DELETE CASCADE;

ALTER TABLE FILM_GENRE
    ADD CONSTRAINT IF NOT EXISTS fk_Film_Genre_GenreID FOREIGN KEY (GENRE_ID)
        REFERENCES GENRES (GENRE_ID) ON DELETE RESTRICT;

ALTER TABLE FILM_GENRE
    ADD CONSTRAINT IF NOT EXISTS UC_Film_Genre_GenreID_FilmID UNIQUE (GENRE_ID, FILM_ID)
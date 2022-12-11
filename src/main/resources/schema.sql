CREATE TABLE IF NOT EXISTS  FRIENDSHIP
(
    USER_ID           INTEGER               not null,
    FRIEND_ID         INTEGER               not null,
    FRIENDSHIP_STATUS CHARACTER VARYING(20) not null,
    constraint FRIENDSHIP_PK
        primary key (USER_ID)
);

CREATE TABLE IF NOT EXISTS  GENRE
(
    GENRE_ID    INTEGER auto_increment
        primary key,
    GENRE_NAME  CHARACTER VARYING(50)  not null,
    DESCRIPTION CHARACTER VARYING(250) not null
);

CREATE TABLE IF NOT EXISTS  FILM_GENRE
(
    GENRE_ID INTEGER auto_increment,
    FILM_ID  INTEGER not null
        primary key,
    constraint FILM_GENRE_GENRE_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRE
);

CREATE TABLE IF NOT EXISTS  RATING
(
    RATING_ID          INTEGER               not null,
    RATING_NAME        CHARACTER VARYING(30) not null,
    RATING_DESCRIPTION CHARACTER VARYING(250),
    constraint RATING_PK
        primary key (RATING_ID)
);

CREATE TABLE IF NOT EXISTS  USERS
(
    USER_ID   INTEGER auto_increment
        primary key,
    USER_NAME CHARACTER VARYING(250),
    EMAIL     CHARACTER VARYING(250) not null,
    LOGIN     CHARACTER VARYING(250) not null,
    BIRTHDAY  DATE                   not null,
    constraint USERS_FRIENDSHIP_USER_ID_FK
        foreign key (USER_ID) references FRIENDSHIP
);

CREATE TABLE IF NOT EXISTS  FILM_LIKES
(
    "FILM_ID " INTEGER not null,
    USER_ID    INTEGER not null,
    constraint FILM_LIKES_PK
        primary key ("FILM_ID "),
    constraint FILM_LIKES_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS
);

CREATE TABLE IF NOT EXISTS  FILM
(
    FILM_ID      INTEGER auto_increment
        primary key,
    FILM_NAME    CHARACTER VARYING(50) not null,
    DESCRIPTION  CHARACTER VARYING(255),
    RELEASE_DATE DATE                  not null,
    DURATION     INTEGER               not null,
    RATING_ID    INTEGER,
    GENRE_ID     INTEGER,
    constraint FILM_FILM_GENRE_FILM_ID_FK
        foreign key (FILM_ID) references FILM_GENRE,
    constraint "FILM_FILM_LIKES_FILM_ID _FK"
        foreign key (FILM_ID) references FILM_LIKES,
    constraint FILM_RATING_RATING_ID_FK
        foreign key (RATING_ID) references RATING
);

-- alter table FRIENDSHIP
--     add constraint FRIENDSHIP_USERS_USER_ID_FK
--         foreign key (USER_ID) references USERS;
--
-- alter table FRIENDSHIP
--     add constraint FRIENDSHIP_USERS_USER_ID_FK_2
--         foreign key (FRIEND_ID) references USERS;


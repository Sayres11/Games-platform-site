# Games-Platform-Site

## Overview
Games-Platform-Site is a web application designed for managing players and their activities in an online game. This platform serves as a central hub for game administrators to interact with player data, monitor activities, and maintain player profiles.
Full Spring MVC CRUD app

## Technology Stack

### Frontend
- **HTML**
- **Thymeleaf**

### Backend
- **Java 1.8**
- **Spring MVC 2.5.1** 
- **Spring Security** 
- **SQLite** 

### Web Server
- **TomCat 9.0.76**

## Features
The application offers a range of features, including:

**Login/Registation**: 
- Offers a secure and streamlined process for user login and registration, enabling players to easily access their accounts and interact with the platform

**Player Management**:
- Storing basic player information, such as name, email address, date of birth
birth date and location. Tracking the creation date and last modification of a player's account.

**Game catalog**:
- Maintaining a list of available games with their unique identifiers and names.
- Allowing players to search for games based on various criteria,

**Tracking participation in games**:
- Recording players participation in various games, creating a many-to-many relationship between
players and games.
- Including game statistics such as the number of hours spent in a game, kills, deaths and the
player's ranking in that game.
- The ability to ban players in specific games.

**Manage related accounts**:
- Allowing players to link additional accounts to their main gaming profile.
- Include information about linked accounts, such as username, email address and
platform.


## Database DDL,DML, Scheme

DDL:
```
create table Games
(
    game_id   INTEGER      not null
        constraint Games_pk
            primary key autoincrement,
    game_name varchar(255) not null
);

create table Players
(
    player_id     INTEGER                not null
        constraint Players_pk
            primary key autoincrement,
    player_name   varchar(82)            not null,
    email         varchar(82)            not null,
    date_of_birth date                   not null,
    created_at    datetime               not null,
    last_modified datetime               not null,
    location      varchar(82)            not null,
    password      varchar(82)            not null,
    role          varcher default 'user' not null
);

create table Game_Participation
(
    Participation_id INTEGER not null
        constraint Game_Participation_pk
            primary key autoincrement,
    player_id        int     not null
        constraint Game_Participation_Players
            references Players,
    game_id          int     not null
        constraint Participation_Games
            references Games,
    hours_played     int     not null,
    join_date        date    not null,
    is_banned        boolean not null,
    kills            int     not null,
    death            int     not null,
    rank             int     not null
);

create table Linked_Accounts
(
    linked_id       int         not null
        constraint Linked_Accounts_pk
            primary key,
    player_id       int         not null
        constraint Linked_Accounts_Players
            references Players,
    account_details varchar(82) not null
);`
```

DML:
```INSERT INTO Players (player_id, player_name, email, date_of_birth, created_at, last_modified, location, password)
VALUES (1, 'Sayres', 'mymail@email.com', '2003-12-04', '2024-01-13 10:37:32', '2024-01-13 11:24:55', 'Warsaw, Poland',
        '123'),
       (2, 'S22141', 'player2@email.com', '2002-07-02', '2023-12-08 15:43:44', '2024-01-13 22:11:54', 'New York, USA',
        '321'),
       (3, 'Git', 'mymail@email.com', '2003-12-04', '2024-01-13 19:00:00', '2024-01-13 11:24:55', 'Toronto, Canada',
        '123'),
       (4, 'Tin', 'Tin@email.com', '2000-06-02', '2024-01-10 18:43:44', '2024-01-13 22:11:54', 'Paris, France', '321'),
       (10, 'Alex', 'user10@example.com', '1995-07-11', '2024-01-15 22:33:27', '2024-01-15 23:33:27', 'Berlin, Germany',
        'twwqbigv'),
       (11, 'Jordan', 'user11@example.com', '1984-02-09', '2024-01-15 22:33:27', '2024-01-16 16:33:27', 'London, UK',
        '23j4ycaa'),
       (12, 'Riley', 'user12@example.com', '1992-08-12', '2024-01-15 22:33:27', '2024-01-16 06:33:27', 'Madrid, Spain',
        'j2gkgj8x'),
       (13, 'Taylor', 'user13@example.com', '1993-03-17', '2024-01-15 22:33:27', '2024-01-16 13:33:27', 'Rome, Italy',
        'zrelm2jj'),
       (14, 'Morgan', 'user14@example.com', '2002-11-18', '2024-01-15 22:33:27', '2024-01-16 05:33:27',
        'Amsterdam, Netherlands', '8p9iob8q'),
       (15, 'Casey', 'user15@example.com', '2002-09-17', '2024-01-15 22:33:27', '2024-01-16 20:33:27',
        'Brussels, Belgium', 'p39wzqsb'),
       (16, 'Drew', 'user16@example.com', '1994-05-30', '2024-01-15 22:33:27', '2024-01-16 20:33:27', 'Vienna, Austria',
        'wck85l4l'),
       (17, 'Jesse', 'user17@example.com', '1993-05-12', '2024-01-15 22:33:27', '2024-01-16 22:33:27',
        'Copenhagen, Denmark', 'fueu4vh6'),
       (18, 'Quinn', 'user18@example.com', '2000-09-02', '2024-01-15 22:33:27', '2024-01-16 04:33:27',
        'Dublin, Ireland', '352wbhdu');

INSERT INTO Games (game_id, game_name)
VALUES (1, 'CS 2'),
       (2, 'DOTA 2'),
       (3, 'Valorant'),
       (4, 'League of Legends');


INSERT INTO Game_Participation (Participation_id, player_id, game_id, hours_played, join_date, is_banned, kills, death,
                                rank)
VALUES (1, 1, 1, 17, '2023-12-01', 0, 255, 270, 2),
       (2, 2, 2, 12, '2024-01-01', 0, 1047, 700, 2),
       (3, 1, 3, 14, '2023-12-25', 0, 754, 523, 3),
       (4, 2, 4, 11, '2023-11-05', 0, 5, 3, 1),
       (5, 3, 2, 15, '2023-12-25', 0, 754, 523, 3),
       (10, 12, 2, 10, '2023-11-19', 1, 866, 963, 5),
       (11, 12, 1, 16, '2023-11-16', 1, 424, 680, 2),
       (12, 4, 3, 3, '2023-11-29', 0, 797, 167, 5),
       (13, 12, 3, 16, '2023-12-17', 0, 480, 44, 3),
       (14, 14, 4, 6, '2023-11-11', 0, 12, 789, 2),
       (15, 13, 2, 13, '2023-12-03', 1, 975, 867, 5),
       (16, 4, 4, 9, '2023-12-13', 0, 392, 802, 5),
       (17, 3, 2, 14, '2023-12-31', 0, 492, 890, 3),
       (18, 13, 2, 17, '2023-11-27', 1, 832, 365, 4);

INSERT INTO Linked_Accounts (linked_id, player_id, account_details)
VALUES (5, 3, 'STEAM: Sayres'),
       (2, 2, 'BATTLNET: AccountDetails2'),
       (3, 3, 'STEAM: AccountDetails3'),
       (4, 4, 'PSN: AccountDetails4');
```



![admin-2024-01-17_00-23](https://github.com/Sayres11/Games-platform-site/assets/44787029/63b906e7-2481-462e-a5fa-7439f809e00b)





## Deployment
1.Build the project: `mvn clean install`

2.Ensure that JDK 1.8 is installed

3.Set up the SQLite database in `UserDAO`, `ParticipationDAO`, `SecurityConfig`, `LinkedAccountDAO`, `GameDAO`

4.Add TomCat Server to IDE


## Screenshots

![image](https://github.com/Sayres11/Games-platform-site/assets/44787029/70e627b7-c550-49e3-a9c1-7333de9af301)

![image](https://github.com/Sayres11/Games-platform-site/assets/44787029/97514fc1-71ac-4d29-a2a8-60bde2404969)

![image](https://github.com/Sayres11/Games-platform-site/assets/44787029/dc18b61d-76b7-48f9-bce1-0cb5867858e5)

![image](https://github.com/Sayres11/Games-platform-site/assets/44787029/83a3c2a5-ab2b-4f82-8387-f4d059b4b40a)

![image](https://github.com/Sayres11/Games-platform-site/assets/44787029/76c0076e-c32a-42ed-a914-c7d1c9b5eb49)

![image](https://github.com/Sayres11/Games-platform-site/assets/44787029/1de6fbed-49fd-4143-9d60-986d1c6519cb)






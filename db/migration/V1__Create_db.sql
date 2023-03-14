create table TOURNAMENT_USER (
        USER_ID int generated always as identity,
        USERNAME varchar(100) not null,
        EMAIL varchar(150) not null,
        FIRST_NAME varchar(150) not null,
        LAST_NAME varchar(150) not null,
        PASSWORD varchar(100) not null,
        ROLE varchar(15) not null,
        PRIMARY KEY (USER_ID)
);

create table TOURNAMENT (
    TOURNAMENT_ID int generated always as identity,
    USER_ID int not null,
    MATCH_UP_ID int not null,
    TEAM_ID int not null,
    NAME varchar(100) not null,
    SIZE int not null,
    CREATION_DATE timestamp,
    GAME varchar(150) not null,
    PRIMARY KEY(TOURNAMENT_ID),
    CONSTRAINT fk_user
        FOREIGN KEY (USER_ID)
            REFERENCES TOURNAMENT_USER(USER_ID),
    CONSTRAINT fk_matchup
        FOREIGN KEY (MATCH_UP_ID)
            REFERENCES MATCH_UP,
    CONSTRAINT fk_team
        FOREIGN KEY (TEAM_ID)
            REFERENCES TEAM
);
create table TEAM(
    TEAM_ID int generated always as identity,
    NAME varchar(256),
    TOURNAMENT_ID int not null,
    PRIMARY KEY(TEAM_ID),
    CONSTRAINT fk_tournament
        FOREIGN KEY (TOURNAMENT_ID)
            REFERENCES TOURNAMENT(TOURNAMENT_ID)
);

create table MATCH_UP(
    MATCH_UP_ID int generated always as identity,
    WINNER_ID int not null,
    LOSER_ID int not null,
    CONSTRAINT fk_winner
        FOREIGN KEY (WINNER_ID)
            REFERENCES TEAM(TEAM_ID),
    CONSTRAINT fk_loser
        FOREIGN KEY (LOSER_ID)
            REFERENCES TEAM(TEAM_ID)

);

create sequence user_id_sequence as int increment by 1 minvalue 1 maxvalue 999999999999 start with 1 CACHE 60 no cycle;
create sequence tournament_id_sequence as int increment by 1 minvalue 1 maxvalue 999999999999 start with 1 CACHE 60 no cycle;
create sequence team_id_sequence as int increment by 1 minvalue 1 maxvalue 999999999999 start with 1 CACHE 60 no cycle;
create sequence match_up_id_sequence as int increment by 1 minvalue 1 maxvalue 999999999999 start with 1 CACHE 60 no cycle;
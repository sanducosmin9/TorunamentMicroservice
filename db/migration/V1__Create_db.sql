create table TOURNAMENT_USER (
        USER_ID int generated always as identity,
        USERNAME varchar(100) not null,
        PASSWORD varchar(100) not null,
        ROLE varchar(15) not null,
        PRIMARY KEY (USER_ID)
);

create table TOURNAMENT (
    TOURNAMENT_ID int generated always as identity,
    USER_ID int not null,
    NAME varchar(100) not null,
    CREATION_DATE timestamp,
    PRIMARY KEY(TOURNAMENT_ID),
    CONSTRAINT fk_user
        FOREIGN KEY (USER_ID)
            REFERENCES TOURNAMENT_USER(USER_ID)
);

create sequence user_id_sequence as int increment by 1 minvalue 1 maxvalue 999999999999 start with 1 CACHE 60 no cycle;
create sequence tournament_id_sequence as int increment by 1 minvalue 1 maxvalue 999999999999 start with 1 CACHE 60 no cycle;
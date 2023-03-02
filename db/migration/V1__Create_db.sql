create table TOURNAMENT_USER (
        USER_ID int generated always as identity,
        USERNAME varchar(100) not null,
        PRIMARY KEY (USER_ID)
);

create sequence user_id_sequence as int increment by 1 minvalue 1 maxvalue 999999999999 start with 1 CACHE 60 no cycle;
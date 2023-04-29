create table if not exists "users"(
    id identity not null,
    login varchar(255) not null,
    password varchar(255) not null
);

alter table if exists "users"
    add constraint if not exists uq_login unique (login);

INSERT into "users" values (1, 'admin', 'password');
INSERT into "users" values (2, 'user', 'password');
create table if not exists "users"(
    id identity not null,
    login varchar(255) not null,
    password varchar(255) not null,
    role varchar(255) not null
);

alter table if exists "users"
    add constraint if not exists uq_login unique (login);

create table if not exists "token" (
    id identity not null,
    refresh_token varchar(255) not null,
    expired_at datetime not null,
    issued_at datetime not null,
    "user_name" bigint not null,
    constraint fk_token_users foreign key ("user_name") references "users"(id)
);

create table if not exists "projects" (
    id identity not null,
    name varchar(255) not null,
    "parent_project" bigint,
    constraint fk_parent_project foreign key ("parent_project") references "projects"(id)
);

create table if not exists "tasks" (
    id identity not null,
    name varchar(255) not null,
    type varchar(7) not null,
    status varchar(8) not null,
    parent_project bigint not null,
    changed_at datetime not null,
    created_at datetime not null,
    constraint fk_task_to_project foreign key (parent_project) references "projects"(id)
);
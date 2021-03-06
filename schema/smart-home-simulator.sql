drop table if exists appliances cascade;

drop table if exists openings cascade;

drop table if exists users cascade;

drop table if exists zones cascade;

drop table if exists homes cascade;

drop table if exists home_groups cascade;

create table homes
(
    id               bigint unsigned auto_increment
        primary key,
    name             varchar(255)  not null,
    date             datetime      not null,
    outside_temp     int           not null,
    security_level   varchar(30)   null,
    auto_mode        int default 1 null,
    simulation_state int default 0 null
)
    collate = utf8mb4_unicode_ci;

create table zones
(
    id          bigint unsigned auto_increment
        primary key,
    name        varchar(255)    not null,
    temperature int default 21  not null,
    home_id     bigint unsigned null,
    group_id    bigint unsigned null,
    overridden  boolean null,
    constraint zones_home_id_foreign
        foreign key (home_id) references homes (id)
)
    collate = utf8mb4_unicode_ci;

create table appliances
(
    id      bigint unsigned auto_increment
        primary key,
    type    varchar(255)    not null,
    name    varchar(255)    not null,
    state   int             not null,
    home_id bigint unsigned null,
    zone_id bigint unsigned null,
    constraint appliances_home_id_foreign
        foreign key (home_id) references homes (id),
    constraint appliances_zone_id_foreign
        foreign key (zone_id) references zones (id)
)
    collate = utf8mb4_unicode_ci;

create table home_groups
(
    id      bigint unsigned auto_increment
        primary key,
    name    varchar(255)    not null,
    home_id bigint unsigned not null,
    temperature int default 20 not null,
    temperature_winter int default 21 not null,
    temperature_summer int default 18 not null,
    heating boolean null
)
    collate = utf8mb4_unicode_ci;

create table openings
(
    id      bigint unsigned auto_increment
        primary key,
    type    varchar(255)       not null,
    state   smallint default 0 not null,
    zone_id bigint unsigned    null,
    blocked boolean null,
    constraint openings_zone_id_foreign
        foreign key (zone_id) references zones (id)
)
    collate = utf8mb4_unicode_ci;

create table users
(
    id       bigint unsigned auto_increment
        primary key,
    name     varchar(255)                                                                   not null,
    email    varchar(255)                                                                   not null,
    role     enum ('ROLE_ADMIN', 'ROLE_PARENT', 'ROLE_USER', 'ROLE_CHILD', 'ROLE_STRANGER') null,
    password varchar(255)                                                                   not null,
    home_id  bigint unsigned                                                                null,
    zone_id  bigint unsigned                                                                null,
    constraint users_email_unique
        unique (email),
    constraint users_home_id_foreign
        foreign key (home_id) references homes (id),
    constraint users_zone_id_foreign
        foreign key (zone_id) references zones (id)
)
    collate = utf8mb4_unicode_ci;


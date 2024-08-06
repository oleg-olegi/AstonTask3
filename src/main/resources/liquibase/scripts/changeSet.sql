-- liquibase formatted sql

-- changeset oshinkevich:1

SET search_path TO public;

CREATE TABLE author
(
    id    BIGSERIAL primary key NOT NULL,
    name  TEXT                  NOT NULL,
    email TEXT NULL
);

CREATE TABLE post
(
    id      BIGSERIAL primary key NOT NULL,
    title   TEXT                  NOT NULL,
    content TEXT                  NOT NULL
);


CREATE TABLE tag
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name TEXT                  NOT NULL
);

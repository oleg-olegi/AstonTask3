-- liquibase formatted sql

-- changeset oshinkevich:1
CREATE TABLE author
(
    id SERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE post
(
    id SERIAL PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    content   VARCHAR(255),
    author_id BIGINT       NOT NULL,
    CONSTRAINT fk_post_author FOREIGN KEY (author_id) REFERENCES author (id)
);

CREATE TABLE tag
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE post_tag
(
    post_id BIGINT NOT NULL,
    tag_id  BIGINT NOT NULL,
    CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES post (id),
    CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tag (id),
    PRIMARY KEY (post_id, tag_id)
);
-- changeset oshinkevich2
CREATE TABLE author_post (
                             author_id BIGINT NOT NULL,
                             post_id BIGINT NOT NULL,
                             CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES author (id),
                             CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES post (id),
                             PRIMARY KEY (author_id, post_id)
);
-- changeset oshinkevich3


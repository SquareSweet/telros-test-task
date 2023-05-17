CREATE TABLE IF NOT EXISTS users
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY,
    email        VARCHAR(200),
    last_name    VARCHAR(200),
    first_name   VARCHAR(200),
    mid_name     VARCHAR(200),
    birthday     TIMESTAMP WITHOUT TIME ZONE,
    phone_number VARCHAR(12),
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email)
);
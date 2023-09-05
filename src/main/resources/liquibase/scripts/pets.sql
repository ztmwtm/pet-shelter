-- liquibase formatted sql

-- changeset rzrazhevskiy:6

CREATE TABLE pets (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       species TEXT,
                       nickname TEXT,
                       is_adopted BOOL
)

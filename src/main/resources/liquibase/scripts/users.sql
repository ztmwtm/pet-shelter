-- liquibase formatted sql

-- changeset rzrazhevskiy:6

CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       chat_id BIGINT,
                       first_name TEXT,
                       last_name TEXT,
                       tg_username TEXT,
                       phone_number TEXT,
                       is_volunteer BOOL,
                       is_adopter BOOL
)

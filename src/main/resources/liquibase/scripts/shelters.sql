-- liquibase formatted sql

-- changeset rzrazhevskiy:6

CREATE TABLE shelters (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name TEXT,
                       adress TEXT,
                       phone_number TEXT,
                       work_schedule TEXT
)
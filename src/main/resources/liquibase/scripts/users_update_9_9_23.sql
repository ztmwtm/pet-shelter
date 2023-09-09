-- liquibase formatted sql

-- changeset kudriavtcev:7

ALTER TABLE users
    DROP COLUMN is_volunteer;
ALTER TABLE users
    DROP COLUMN is_adopter;
ALTER TABLE users
    ADD COLUMN role ENUM (
        'ADMINISTRATOR',
        'USER',
        'ADOPTER',
        'VOLUNTEER'
        );

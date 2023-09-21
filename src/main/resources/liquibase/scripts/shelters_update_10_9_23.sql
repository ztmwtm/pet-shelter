-- liquibase formatted sql

-- changeset kudriavtcev:10

ALTER TABLE shelters
    ADD COLUMN type ENUM (
        'DOG',
        'CAT' );
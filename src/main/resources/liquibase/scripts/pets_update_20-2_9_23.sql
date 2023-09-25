-- liquibase formatted sql

-- changeset SlavaMarchkov:13
ALTER TABLE pets
    ADD COLUMN pet_type ENUM('CAT', 'DOG') NULL;
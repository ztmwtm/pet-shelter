-- liquibase formatted sql

-- changeset SlavaMarchkov:11
ALTER TABLE pets
    ADD COLUMN day_of_adopt DATE;
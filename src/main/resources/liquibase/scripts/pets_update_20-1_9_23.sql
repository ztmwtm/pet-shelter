-- liquibase formatted sql

-- changeset SlavaMarchkov:12
ALTER TABLE pets
    ADD COLUMN days_to_adaptation INT DEFAULT NULL;
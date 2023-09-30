-- liquibase formatted sql

-- changeset SlavaMarchkov:16

ALTER TABLE pets
    DROP COLUMN is_adopted;

ALTER TABLE pets
    ADD COLUMN pet_status ENUM (
        'AVAILABLE',
        'CHOSEN',
        'ADOPTED',
        'KEPT'
        ) DEFAULT 'AVAILABLE';
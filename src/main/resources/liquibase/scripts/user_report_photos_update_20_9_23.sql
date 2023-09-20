-- liquibase formatted sql

-- changeset SlavaMarchkov:15
ALTER TABLE user_report_photos
    ADD COLUMN title VARCHAR(255) NULL;
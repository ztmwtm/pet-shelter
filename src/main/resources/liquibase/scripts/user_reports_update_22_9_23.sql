-- liquibase formatted sql

-- changeset zrazhevskiy:16

ALTER TABLE user_reports
    ADD COLUMN date_of_creation date;
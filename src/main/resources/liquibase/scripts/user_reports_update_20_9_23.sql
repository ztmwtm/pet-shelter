-- liquibase formatted sql

-- changeset zrazhevskiy:14

ALTER TABLE user_reports
    ADD COLUMN status ENUM (
        'CREATED',
        'ON_VERIFICATION',
        'VERIFIED',
        'DECLINED'
        );
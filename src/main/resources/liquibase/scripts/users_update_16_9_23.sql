-- liquibase formatted sql

-- changeset zrazhevskiy:11

ALTER TABLE users ADD COLUMN day_of_adopt Date;
ALTER TABLE users ADD COLUMN days_to_adaptation INT;

-- changeset zrazhevskiy:12

ALTER TABLE users DROP COLUMN day_of_adopt;
ALTER TABLE users DROP COLUMN days_to_adaptation;

ALTER TABLE pets ADD COLUMN day_of_adopt Date;
ALTER TABLE pets ADD COLUMN days_to_adaptation INT;
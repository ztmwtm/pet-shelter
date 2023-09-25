-- liquibase formatted sql

-- changeset kudriavtcev:9

ALTER TABLE shelters ADD COLUMN latitude FLOAT;
ALTER TABLE shelters ADD COLUMN longitude FLOAT;
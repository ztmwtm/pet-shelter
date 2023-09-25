-- liquibase formatted sql

-- changeset kudriavtcev:10

ALTER TABLE users ADD COLUMN selected_shelter_id BIGINT;
ALTER TABLE users ADD CONSTRAINT FOREIGN KEY (selected_shelter_id) REFERENCES shelters (id);
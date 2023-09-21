-- liquibase formatted sql

-- changeset kudriavtcev:8
ALTER TABLE pets
    ADD COLUMN user_id BIGINT;
ALTER TABLE pets
    ADD CONSTRAINT FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE pets
    ADD COLUMN shelter_id BIGINT;
ALTER TABLE pets
    ADD CONSTRAINT FOREIGN KEY (shelter_id) REFERENCES shelters (id);
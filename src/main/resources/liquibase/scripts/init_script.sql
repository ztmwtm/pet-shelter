-- liquibase formatted sql
-- changeset kudriavtcev:1
CREATE TABLE pet_shelter_user
(
    id   INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

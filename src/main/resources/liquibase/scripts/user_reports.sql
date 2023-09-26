-- liquibase formatted sql

-- changeset rzrazhevskiy:6

CREATE TABLE user_reports (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       user_id BIGINT,
                       pet_id BIGINT,
                       shelter_id BIGINT,
                       pet_diet TEXT,
                       health TEXT,
                       behavior TEXT,
                       FOREIGN KEY (user_id) REFERENCES users (id),
                       FOREIGN KEY (pet_id) REFERENCES pets (id),
                       FOREIGN KEY (shelter_id) REFERENCES shelters (id)
)
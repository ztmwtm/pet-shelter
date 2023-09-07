-- liquibase formatted sql

-- changeset rzrazhevskiy:6

CREATE TABLE shelter_documents (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       shelter_id BIGINT,
                       file_path TEXT,
                       file_size BIGINT,
                       media_type TEXT,
                       data BINARY,
                       title TEXT,
                       FOREIGN KEY (shelter_id) REFERENCES shelters (id)
)
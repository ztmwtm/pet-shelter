-- liquibase formatted sql

-- changeset rzrazhevskiy:6

CREATE TABLE user_report_photos (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       user_report_id BIGINT,
                       file_path TEXT,
                       file_size BIGINT,
                       media_type TEXT,
                       data BINARY,
                       FOREIGN KEY (user_report_id) REFERENCES user_reports (id)
)
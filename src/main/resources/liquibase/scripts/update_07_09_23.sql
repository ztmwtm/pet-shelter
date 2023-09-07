-- liquibase formatted sql

-- changeset Kudriavtcev:7

# ALTER TABLE pets DROP COLUMN is_adopted;
# ALTER TABLE pets ADD COLUMN shelter_id BIGINT;
ALTER TABLE pets ADD CONSTRAINT FOREIGN KEY (shelter_id) REFERENCES shelters (id);

ALTER TABLE shelter_documents DROP COLUMN data;

ALTER TABLE user_report_photos DROP COLUMN data;

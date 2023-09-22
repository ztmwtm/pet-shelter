-- liquibase formatted sql

-- changeset SlavaMarchkov:14
ALTER TABLE shelter_documents
    ADD COLUMN type ENUM('short description', 'driving directions', 'entry pass', 'safety rules') NULL;
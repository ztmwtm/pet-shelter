-- liquibase formatted sql

-- changeset zrazhevskiy:17

alter table users
    add column active_report_for_checking BIGINT;
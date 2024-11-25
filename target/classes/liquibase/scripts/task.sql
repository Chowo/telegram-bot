-- liquibase formatted sql

-- changeset wkhod:1
CREATE TABLE IF NOT EXISTS task(
    id                  BIGSERIAL   PRIMARY KEY,
    chat_id             VARCHAR     NOT NULL,
    text                VARCHAR     NOT NULL,
    notification_date   TIMESTAMP   NOT NULL,
    entry_date          TIMESTAMP   NOT NULL
    );
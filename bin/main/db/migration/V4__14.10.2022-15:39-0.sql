ALTER TABLE users
    ADD COLUMN version BIGINT;

ALTER TABLE persons
    ADD COLUMN version BIGINT;

ALTER TABLE business_units
    ADD COLUMN version BIGINT;

ALTER TABLE addresses
    ADD COLUMN version BIGINT;
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS persons_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS business_units_seq START WITH 1 INCREMENT BY 50;


CREATE TABLE business_units
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_business_units PRIMARY KEY (id)
);

CREATE TABLE persons
(
    id         BIGINT      NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    CONSTRAINT pk_persons PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                        BIGINT       NOT NULL,
    user_id                   VARCHAR(255) NOT NULL,
    person_id                 BIGINT,
    business_unit_id          BIGINT,
    email                     VARCHAR(120) NOT NULL,
    encrypted_password        VARCHAR(255) NOT NULL,
    email_verification_token  VARCHAR(255),
    email_verification_status BOOLEAN      NOT NULL,
    is_active                 BOOLEAN      NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_BUSINESS_UNIT FOREIGN KEY (business_unit_id) REFERENCES business_units (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_PERSON FOREIGN KEY (person_id) REFERENCES persons (id);


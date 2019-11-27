CREATE SCHEMA IF NOT EXISTS clinic;
SET SCHEMA 'clinic';

CREATE TABLE app_user
(
    id              BIGSERIAL NOT NULL
        CONSTRAINT app_user_pkey
            PRIMARY KEY,
    created_datetime TIMESTAMP,
    updated_datetime TIMESTAMP,
    version         INTEGER,
    full_name        VARCHAR(255)
        CONSTRAINT app_user_full_name_key
            UNIQUE NOT NULL,
    password        VARCHAR(255),
    username        VARCHAR(255)
        CONSTRAINT app_user_username_key
            UNIQUE NOT NULL
);

CREATE TABLE authority
(
    id              BIGSERIAL NOT NULL
        CONSTRAINT authority_pkey
        PRIMARY KEY,
    created_datetime TIMESTAMP,
    updated_datetime TIMESTAMP,
    version         INTEGER,
    name            VARCHAR(255)
        CONSTRAINT authority_name_key
        UNIQUE NOT NULL
);

CREATE TABLE app_user_authority
(
    app_user_id      BIGINT NOT NULL
        CONSTRAINT user_authority_app_user_fkey
            REFERENCES app_user,
    authority_id BIGINT NOT NULL
        CONSTRAINT user_authority_authority_fkey
            REFERENCES authority,
    CONSTRAINT user_authority_pkey
        PRIMARY KEY (app_user_id, authority_id)
);

CREATE TABLE patient
(
    id              BIGSERIAL NOT NULL
        CONSTRAINT patient_pkey
        PRIMARY KEY,
    created_datetime TIMESTAMP,
    updated_datetime TIMESTAMP,
    version         INTEGER,
    full_name        VARCHAR(255)
        CONSTRAINT patient_full_name_key
        UNIQUE NOT NULL,
    diagnosis        VARCHAR(255) NOT NULL,
    insurance_number VARCHAR(255)
        CONSTRAINT patient_insurance_number_key
            UNIQUE NOT NULL,
    patient_status   VARCHAR(255) NOT NULL
);

CREATE TABLE time_pattern
(
    id              BIGSERIAL NOT NULL
        CONSTRAINT time_pattern_pkey
        PRIMARY KEY,
    created_datetime TIMESTAMP,
    updated_datetime TIMESTAMP,
    version         INTEGER,
    cycle_length     SMALLINT NOT NULL,
    is_week_cycle     boolean NOT NULL,
    name            VARCHAR(255) NOT NULL
);

CREATE TABLE time_pattern_item
(
    id              BIGSERIAL NOT NULL
        CONSTRAINT time_pattern_item_pkey
        PRIMARY KEY,
    created_datetime TIMESTAMP,
    updated_datetime TIMESTAMP,
    version         INTEGER,
    day_of_cycle      SMALLINT NOT NULL,
    time            TIME NOT NULL,
    time_pattern_id  BIGINT NOT NULL
        CONSTRAINT time_pattern_item_time_pattern_fkey
        REFERENCES time_pattern
);

CREATE TABLE treatment
(
    id              BIGSERIAL NOT NULL
        CONSTRAINT treatment_pkey
        PRIMARY KEY,
    created_datetime TIMESTAMP,
    updated_datetime TIMESTAMP,
    version         INTEGER,
    name            VARCHAR(255)
        CONSTRAINT treatment_name_key
        UNIQUE NOT NULL,
    type            VARCHAR(255) NOT NULL
);

CREATE TABLE prescription
(
    id                  BIGSERIAL NOT NULL
        CONSTRAINT prescription_pkey
        PRIMARY KEY,
    created_datetime    TIMESTAMP,
    updated_datetime    TIMESTAMP,
    version             INTEGER,
    dosage              REAL,
    dosage_info         VARCHAR(255),
    prescription_status VARCHAR(255) NOT NULL,
    start_date          TIMESTAMP NOT NULL,
    end_date            TIMESTAMP NOT NULL,
    doctor_id           BIGINT NOT NULL
        CONSTRAINT prescription_doctor_fkey
        REFERENCES app_user,
    patient_id          BIGINT NOT NULL
        CONSTRAINT prescription_patient_fkey
        REFERENCES patient,
    time_pattern_id     BIGINT NOT NULL
        CONSTRAINT prescription_time_pattern_fkey
        REFERENCES time_pattern,
    treatment_id        BIGINT NOT NULL
        CONSTRAINT prescription_treatment_fkey
        REFERENCES treatment
);

CREATE TABLE event
(
    id              BIGSERIAL NOT NULL
        CONSTRAINT event_pkey
        PRIMARY KEY,
    created_datetime TIMESTAMP,
    updated_datetime TIMESTAMP,
    version          INTEGER,
    datetime         TIMESTAMP,
    event_status     VARCHAR(255) NOT NULL,
    cancel_reason    VARCHAR(255),
    nurse_id         BIGINT
        CONSTRAINT event_app_user_fkey
            REFERENCES app_user,
    patient_id       BIGINT       NOT NULL
        CONSTRAINT event_patient_fkey
        REFERENCES patient,
    prescription_id  BIGINT       NOT NULL
        CONSTRAINT event_prescription_fkey
        REFERENCES prescription,
    dosage           REAL         NOT NULL,
    dosage_info      VARCHAR(255),
    doctor_id        BIGINT       NOT NULL
        CONSTRAINT event_doctor_fkey
            REFERENCES app_user,
    time_pattern_id  BIGINT       NOT NULL
        CONSTRAINT event_time_pattern_fkey
            REFERENCES time_pattern,
    treatment_id     BIGINT       NOT NULL
        CONSTRAINT event_treatment_fkey
            REFERENCES treatment
);




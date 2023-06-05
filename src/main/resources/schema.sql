/** Devamı Vt ile Hazırlanacak olursa vt şablonu */
/* app prop da kapali vt */




CREATE SCHEMA eteration;

CREATE TABLE eteration.account
(
    id            bigint         NOT NULL PRIMARY KEY,
    owner         VARCHAR(50)    NOT NULL,
    account_number CHAR(8)        NOT NULL,
    balance       NUMERIC(10, 3) NOT NULL,
    UNIQUE (account_number)
);
/*
CREATE SEQUENCE eteration.transaction_sequence START WITH 5;
CREATE TABLE eteration.transaction
(
    id                bigint         NOT NULL PRIMARY KEY,
    source_account_id bigint         NOT NULL REFERENCES eteration.account (id),
    target_account_id bigint         NOT NULL REFERENCES eteration.account (id),
    target_owner_name varchar(50)    NOT NULL,
    completion_date   TIMESTAMP,
    date DATE NOT NULL,
    amount NUMERIC(10,3) NOT NULL,
    type varchar(50)    NOT NULL,
    approval_code varchar(50) NOT NULL
);

*/



CREATE SEQUENCE eteration.transaction_sequence START WITH 5;
CREATE TABLE eteration.transaction (
    id bigint NOT NULL PRIMARY KEY,
    source_account_id bigint NOT NULL REFERENCES eteration.account(id),
    target_account_id bigint NOT NULL REFERENCES eteration.account(id),
    -- Partially denormalize for performance
    target_owner_name varchar(50) NOT NULL,
    amount NUMERIC(10,3) NOT NULL,
    initiation_date timestamp NOT NULL,
    completion_date TIMESTAMP,
    reference VARCHAR(255),
    latitude REAL,
    longitude REAL
);

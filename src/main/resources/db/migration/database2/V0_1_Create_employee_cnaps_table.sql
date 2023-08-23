\c cnaps ;
CREATE TYPE sex AS ENUM ('H', 'F');
CREATE TYPE csp AS ENUM (
    'AGRICULTURAL_WORKERS',
    'CRAFTSMEN_AND_ARTISANS',
    'TRADERS_AND_MERCHANTS',
    'CIVIL_SERVANTS_AND_PROFESSIONALS',
    'UNSKILLED_LABORERS'
);
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS employee (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name VARCHAR,
    last_name VARCHAR NOT NULL,
    registration_number VARCHAR NOT NULL,
    personal_email VARCHAR NOT NULL UNIQUE,
    cin VARCHAR NOT NULL CHECK (cin ~ '^[0-9]+$'),
    cnaps VARCHAR NOT NULL CHECK (cnaps ~ '^[A-Za-z0-9]+$'),
    children_number INTEGER DEFAULT 0 CHECK (children_number > -1),
    birth_date DATE NOT NULL,
    entrance_date DATE NOT NULL,
    departure_date DATE,
    sex SEX NOT NULL,
    csp CSP NOT NULL,
    image TEXT,
    professional_email VARCHAR NOT NULL UNIQUE,
    address VARCHAR NOT NULL,
    end_to_end_id UUID -- Nouveau champ "end_to_end_id"
);



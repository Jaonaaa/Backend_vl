CREATE DATABASE eval;
CREATE TABLE unit (
    id SERIAL PRIMARY KEY,
    label VARCHAR(50) NOT NULL
);
-- CLIENT => ROLE = USER
--
CREATE TABLE _user ( 
id SERIAL PRIMARY KEY
);
CREATE TABLE building_finition (
    id SERIAL PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    percent NUMERIC(10, 2)
);
CREATE TABLE lieu (
    id SERIAL PRIMARY KEY,
    label VARCHAR(255) NOT NULL
);
-- CREATE TABLE finition_by_building_type (
--     id SERIAL PRIMARY KEY,
--     id_building_type INT REFERENCES building_type (id),
--     id_building_finition INT REFERENCES building_finition (id),
--     percent NUMERIC(10, 2) NOT NULL
-- );
CREATE TABLE building_type (
    id SERIAL PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    duration NUMERIC(10),
    --jour
    surface NUMERIC(10, 2),
);
CREATE TABLE building_type_description (
    id SERIAL PRIMARY KEY,
    id_building_type INT REFERENCES building_type (id),
    description VARCHAR(255) NOT NULL
);
CREATE TABLE devis (
    id SERIAL PRIMARY KEY,
    -- name VARCHAR(120) NOT NULL,
    id_client INT REFERENCES _user (id),
    creation_date timestamp NOT NULL,
    -- id_finition_by_building_type INT REFERENCES finition_by_building_type (id),
    begin_date timestamp NOT NULL,
    total_price NUMERIC(10, 2),
    end_date timestamp,
    id_building_type INT REFERENCES building_type (id),
    id_building_finition INT REFERENCES building_finition (id),
    taux_finition NUMERIC(10, 2),
    -- stocked
    building_finition_label VARCHAR(100),
    building_finition_percent NUMERIC(10, 2),
    building_type_label VARCHAR(100),
    building_type_duration NUMERIC(10),
    building_type_price NUMERIC(10, 2),
    ref_devis VARCHAR(100) UNIQUE,
);
ALTER TABLE devis
ADD COLUMN id_finition_by_building_type INT REFERENCES finition_by_building_type (id);
---
ALTER TABLE devis DROP COLUMN id_finition_by_building_type;
ALTER TABLE devis DROP COLUMN name;
CREATE TABLE devis_set_details (
    id SERIAL PRIMARY KEY,
    id_devis INT REFERENCES devis (id),
    works_label VARCHAR(255) NOT NULL,
    unit VARCHAR(255) NOT NULL,
    quantity NUMERIC(10, 2),
    pu NUMERIC(10, 2),
    parent_devis_set_details_id INT REFERENCES devis_set_details(id),
);
CREATE TABLE payement (
    id SERIAL PRIMARY KEY,
    payement_time timestamp,
    id_client INT REFERENCES _user(id),
    id_devis INT REFERENCES devis (id),
    amount NUMERIC(10, 2)
);
-----
CREATE TABLE works (
    id SERIAL PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    parent_works_id INT REFERENCES works(id)
);
--ALTER TABLE works DROP COLUMN id_works;
CREATE TABLE works_in_devis_details (
    id SERIAL PRIMARY KEY,
    id_unit INT REFERENCES unit (id),
    quantity NUMERIC(10, 2),
    pu NUMERIC(10, 2)
);
CREATE TABLE works_in_devis (
    id SERIAL PRIMARY KEY,
    id_devis INT REFERENCES devis (id),
    id_works INT REFERENCES works (id),
    works_in_devis_details INT REFERENCES works_in_devis_details (id)
);
--PREDEFINED 
---  
CREATE TABLE works_predefined_by_building_type (
    id SERIAL PRIMARY KEY,
    id_building_type INT REFERENCES building_type (id),
    label VARCHAR(255) NOT NULL,
    parent_works_predefined_by_building_type_id INT REFERENCES works_predefined_by_building_type(id),
    id_works_in_devis_details INT REFERENCES works_in_devis_details (id)
);
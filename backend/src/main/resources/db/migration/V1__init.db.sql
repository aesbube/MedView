DROP TABLE IF EXISTS "appointments";
DROP TABLE IF EXISTS "medical_records";
DROP TABLE IF EXISTS "medications";
DROP TABLE IF EXISTS "patient_medications";
DROP TABLE IF EXISTS "patients";
DROP TABLE IF EXISTS "roles";
DROP TABLE IF EXISTS "users";


CREATE TABLE roles (
    role_id SMALLSERIAL PRIMARY KEY,
    role_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE specialties (
    specialty_id SMALLSERIAL PRIMARY KEY,
    specialty_name VARCHAR(255) UNIQUE NOT NULL
);

-- Create the users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    surname VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(50) UNIQUE,
    role_id SMALLINT NOT NULL,
    entity_id BIGINT,
    enabled BIT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Create the patients table
CREATE TABLE patients (
    patient_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT FOREIGN KEY,
    date_of_birth DATE,
    gender VARCHAR(50),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create the doctors table
CREATE TABLE doctors (
    doctor_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT FOREIGN KEY,
    specialty_id SMALLINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')
    FOREIGN KEY (user_id) REFERENCES users(id)
    FOREIGN KEY (specialty_id) REFERENCES specialties(id)
);


-- Sample Data (Optional, but often helpful for initial setup)
INSERT INTO roles (role_name) VALUES ('Admin'), ('General Practitioner'), ('Specialist') , ('Patient');
INSERT INTO specialties (role_name) VALUES ('Cardiology'), ('Dermatology'), ('Neurology') , ('Oncology');

INSERT INTO users (username, password, name, surname, email, phone, role_id, entity_id, enabled)
VALUES
('bugs.bunny', 'carrots', 'bugs', 'bunny', 'buggs.bunny@sorsix.com', '+38970123456', 2, null, 1),
('daffy.duck', 'money', 'daffy', 'duck', 'daffy.duck@sorsix.com', '+38970987654', 3, null, 1),
('johnny.bravo', 'ladies', 'johnny', 'bravo', 'johnny.bravo@sorsix.com', '+389 78 008 135', 4, null, 1),

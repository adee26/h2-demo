CREATE TABLE users(
    id serial PRIMARY KEY NOT NULL,
    first_name VARCHAR,
    last_name varchar,
    phone_number VARCHAR,
    email VARCHAR,
    enabled boolean
)

CREATE TABLE IF NOT EXISTS users (
    id bigserial PRIMARY KEY,
    username VARCHAR NOT NULL,
    phone varchar NOT NULL,
    email VARCHAR,
    password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS body_types (
    id serial PRIMARY KEY,
    body_type VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS brands (
    id serial PRIMARY KEY,
    brand_name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS models (
    id serial PRIMARY KEY,
    model_name VARCHAR NOT NULL,
    brand_id INTEGER NOT NULL REFERENCES brands (id)
);

CREATE TABLE IF NOT EXISTS advertisements (
    id bigserial PRIMARY KEY,
    description TEXT NOT NULL,
    manufacture_year INTEGER NOT NULL,
    status boolean DEFAULT FALSE,
    model_id INTEGER NOT NULL REFERENCES models (id),
    body_type_id INTEGER NOT NULL REFERENCES body_types (id),
    author_id BIGINT NOT NULL REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS photos (
    id bigserial PRIMARY KEY,
    ad_id BIGINT NOT NULL REFERENCES advertisements (id)
);






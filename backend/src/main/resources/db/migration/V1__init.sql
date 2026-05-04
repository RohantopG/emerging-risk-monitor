CREATE TABLE risks (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255),
                       description TEXT,
                       status VARCHAR(50),
                       score INT,
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP
);
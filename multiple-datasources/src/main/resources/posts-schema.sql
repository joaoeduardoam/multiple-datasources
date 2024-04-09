    DROP TABLE IF EXISTS post;

    CREATE TABLE post (
        id SERIAL PRIMARY KEY, text VARCHAR(255) NOT NULL
    );

    INSERT INTO
        post (text) VALUES ('Tutorial Multiplos DBs com Spring');
    DROP TABLE IF EXISTS comment;

    CREATE TABLE comment (
        id SERIAL PRIMARY KEY, text VARCHAR(255) NOT NULL, postid BIGINT NOT NULL
    );

    INSERT INTO comment (text, postid) VALUES ('Comenta o video', 1);
    INSERT INTO comment (text, postid) VALUES ('Curte o video', 1);
    INSERT INTO comment (text, postid) VALUES ('Compartilha!', 1);
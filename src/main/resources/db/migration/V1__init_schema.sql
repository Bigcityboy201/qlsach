CREATE TABLE author (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP
);

CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
    author_id INTEGER NOT NULL,
    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE review (
    id SERIAL PRIMARY KEY,
    content VARCHAR(1000) NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
    book_id INTEGER NOT NULL,
    CONSTRAINT fk_review_book FOREIGN KEY (book_id) REFERENCES book(id)
);

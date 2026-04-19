INSERT INTO author (name, create_at, update_at)
VALUES ('Nguyen Nhat Anh', NOW(), NOW()),
       ('J. K. Rowling', NOW(), NOW()),
       ('Haruki Murakami', NOW(), NOW());

INSERT INTO book (name, create_at, update_at, author_id)
VALUES ('Mat Biec', NOW(), NOW(), 1),
       ('Harry Potter and the Sorcerer''s Stone', NOW(), NOW(), 2),
       ('Norwegian Wood', NOW(), NOW(), 3);

INSERT INTO review (content, create_at, update_at, book_id)
VALUES ('Cam dong va gan gui.', NOW(), NOW(), 1),
       ('Phep thuat va phieu luu rat hay.', NOW(), NOW(), 2),
       ('Sau lang va day cam xuc.', NOW(), NOW(), 3);

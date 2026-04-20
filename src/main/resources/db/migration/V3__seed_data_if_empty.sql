DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM author) THEN
        INSERT INTO author (name, create_at, update_at)
        VALUES ('Nguyen Nhat Anh', NOW(), NOW()),
               ('J. K. Rowling', NOW(), NOW()),
               ('Haruki Murakami', NOW(), NOW());
    END IF;

    IF NOT EXISTS (SELECT 1 FROM book) THEN
        INSERT INTO book (name, create_at, update_at, author_id)
        SELECT 'Mat Biec', NOW(), NOW(), a.id
        FROM author a
        WHERE a.name = 'Nguyen Nhat Anh'
        LIMIT 1;

        INSERT INTO book (name, create_at, update_at, author_id)
        SELECT 'Harry Potter and the Sorcerer''s Stone', NOW(), NOW(), a.id
        FROM author a
        WHERE a.name = 'J. K. Rowling'
        LIMIT 1;

        INSERT INTO book (name, create_at, update_at, author_id)
        SELECT 'Norwegian Wood', NOW(), NOW(), a.id
        FROM author a
        WHERE a.name = 'Haruki Murakami'
        LIMIT 1;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM review) THEN
        INSERT INTO review (content, create_at, update_at, book_id)
        SELECT 'Cam dong va gan gui.', NOW(), NOW(), b.id
        FROM book b
        WHERE b.name = 'Mat Biec'
        LIMIT 1;

        INSERT INTO review (content, create_at, update_at, book_id)
        SELECT 'Phep thuat va phieu luu rat hay.', NOW(), NOW(), b.id
        FROM book b
        WHERE b.name = 'Harry Potter and the Sorcerer''s Stone'
        LIMIT 1;

        INSERT INTO review (content, create_at, update_at, book_id)
        SELECT 'Sau lang va day cam xuc.', NOW(), NOW(), b.id
        FROM book b
        WHERE b.name = 'Norwegian Wood'
        LIMIT 1;
    END IF;
END
$$;

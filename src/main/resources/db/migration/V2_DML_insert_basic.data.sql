INSERT INTO category (name)
VALUES ('Fantasy');
INSERT INTO category (name)
VALUES ('Thriller');
INSERT INTO category (name)
VALUES ('Science Fiction');
INSERT INTO category (name)
VALUES ('Mystery');
INSERT INTO category (name)
VALUES ('Classic Literature');
INSERT INTO category (name)
VALUES ('Biography');
INSERT INTO category (name)
VALUES ('Historical Fiction');
INSERT INTO category (name)
VALUES ('Horror');
INSERT INTO category (name)
VALUES ('Non-Fiction');

INSERT INTO author (name, surname)
VALUES ('Andrzej', 'Sapkowski');
INSERT INTO author (name, surname)
VALUES ('J.R.R.', 'Tolkien');
INSERT INTO author (name, surname)
VALUES ('Brandon', 'Sanderson');
INSERT INTO author (name, surname)
VALUES ('Gillian', 'Flynn');
INSERT INTO author (name, surname)
VALUES ('Stieg', 'Larsson');
INSERT INTO author (name, surname)
VALUES ('Dan', 'Brown');
INSERT INTO author (name, surname)
VALUES ('Frank', 'Herbert');
INSERT INTO author (name, surname)
VALUES ('William', 'Gibson');
INSERT INTO author (name, surname)
VALUES ('Orson Scott', 'Card');
INSERT INTO author (name, surname)
VALUES ('Agatha', 'Christie');
INSERT INTO author (name, surname)
VALUES ('Paula', 'Hawkins');
INSERT INTO author (name, surname)
VALUES ('Jane', 'Austen');
INSERT INTO author (name, surname)
VALUES ('Harper', 'Lee');
INSERT INTO author (name, surname)
VALUES ('George', 'Orwell');
INSERT INTO author (name, surname)
VALUES ('Anne', 'Frank');
INSERT INTO author (name, surname)
VALUES ('Walter', 'Isaacson');
INSERT INTO author (name, surname)
VALUES ('Jeannette', 'Walls');
INSERT INTO author (name, surname)
VALUES ('Anthony', 'Doerr');
INSERT INTO author (name, surname)
VALUES ('Markus', 'Zusak');
INSERT INTO author (name, surname)
VALUES ('Kristin', 'Hannah');
INSERT INTO author (name, surname)
VALUES ('Bram', 'Stoker');
INSERT INTO author (name, surname)
VALUES ('Stephen', 'King');
INSERT INTO author (name, surname)
VALUES ('Mary', 'Shelley');
INSERT INTO author (name, surname)
VALUES ('Nassim', 'Taleb');
INSERT INTO author (name, surname)
VALUES ('Tara', 'Westover');
INSERT INTO author (name, surname)
VALUES ('Michelle', 'Obama');

INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Witcher: Blood of Elves',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        29.99, 'witcher.jpg', NOW(), 1, 1);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('The Hobbit',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        24.95, 'hobbit.jpg', NOW(), 1, 2);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Mistborn: The Final Empire',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        19.99, 'mistborn.jpg', NOW(), 1, 3);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Gone Girl',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        22.50, 'goneGirl.jpg', NOW(), 2, 4);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('The Girl with the Dragon Tattoo',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        28.75, 'theGirlDragon.jpg', NOW(), 2, 5);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('The Da Vinci Code',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        32.00, 'daVinci.jpg', NOW(), 2, 6);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Dune',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        27.50, 'dune.jpg', NOW(), 3, 7);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Neuromancer',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        25.50, 'neuromancer.jpg', NOW(), 3, 8);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Ender''s Game',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        21.99, 'ender.jpg', NOW(), 3, 9);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('And Then There Were None',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        19.95, 'andThen.jpg', NOW(), 4, 10);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('The Girl on the Train',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        23.75, 'theGirlTrain.jpg', NOW(), 4, 11);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('A.B.C.',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        31.50, 'abc.jpg', NOW(), 4, 10);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Pride and Prejudice',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        26.95, 'pride.jpg', NOW(), 5, 12);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('To Kill a Mockingbird',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        29.99, 'mockingbird.jpg', NOW(), 5, 13);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('1984',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        24.50, '1984.jpg', NOW(), 5, 14);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('The Diary of a Young Girl',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        18.95, 'diary.jpg', NOW(), 6, 15);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Steve Jobs',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        20.99, 'steveJobs.jpg', NOW(), 6, 16);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('The Glass Castle',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        23.50, 'theGlass.jpg', NOW(), 6, 17);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('All the Light We Cannot See',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        27.95, 'light.jpg', NOW(), 7, 18);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('The Book Thief',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        25.99, 'theBook.jpg', NOW(), 7, 19);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('The Nightingale',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        29.50, 'nightingale.jpg', NOW(), 7, 20);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Dracula',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        18.50, 'dracula.jpg', NOW(), 8, 21);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('The Shining',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        21.95, 'theShining.jpg', NOW(), 8, 22);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Frankenstein',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        26.50, 'frankenstein.jpg', NOW(), 8, 23);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('The Black Swan',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        30.00, 'swan.jpg', NOW(), 9, 24);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Educated',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        24.99, 'educated.jpg', NOW(), 9, 25);
INSERT INTO book (title, description, price, image_name, create_date, category_id, author_id)
VALUES ('Becoming',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        28.50, 'becoming.jpg', NOW(), 9, 26);

INSERT INTO role (id, name)
VALUES (1, 'USER');
INSERT INTO role (id, name)
VALUES (2, 'ADMIN');

INSERT INTO user (username, password, email, role_id)
VALUES ('user1', '$2a$12$am75LR/Pa6oadlDEt5AmselVElcv9GtOa9lYk2pOEK6SYd5qoPUd2', 'user1@example.com', 1);

INSERT INTO user (username, password, email, role_id)
VALUES ('admin1', '$2a$12$vdXsCkMcWC7dskx7LC.nTuGmlHvRWkOKpP3c14QWzFE/OYPq3lnxK', 'admin1@example.com', 2);
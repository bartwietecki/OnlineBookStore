CREATE TABLE category
(
    id     BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL
);

CREATE TABLE author
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL
);

CREATE TABLE book
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(100)  NOT NULL,
    description VARCHAR(600)  NOT NULL,
    price       DECIMAL(9, 2) NOT NULL,
    image_name  VARCHAR(50)   NOT NULL,
    create_date DATETIME      NOT NULL,
    category_id BIGINT        NOT NULL,
    author_id   BIGINT        NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id),
    FOREIGN KEY (author_id) REFERENCES author (id)
);

CREATE TABLE customer_order
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_full_name VARCHAR(100) NOT NULL,
    customer_email     VARCHAR(100) NOT NULL,
    city               VARCHAR(100) NOT NULL,
    zip_code           VARCHAR(10)  NOT NULL,
    street             VARCHAR(100) NOT NULL,
    street_no          VARCHAR(10),
    home_no            VARCHAR(10),
    price              DECIMAL(9, 2),
    create_date        DATETIME,
    order_status       VARCHAR(500)
);

CREATE TABLE role
(
    id   BIGINT PRIMARY KEY NOT NULL,
    name VARCHAR(100)       NOT NULL
);

CREATE TABLE user
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    role_id  BIGINT       NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE customer_order_book
(
    book_id  BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book (id),
    FOREIGN KEY (order_id) REFERENCES customer_order (id)
);

CREATE TABLE book_author
(
    book_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book (id),
    FOREIGN KEY (author_id) REFERENCES author (id)
);
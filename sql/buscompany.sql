DROP
    DATABASE IF EXISTS buscompany;
CREATE
    DATABASE `buscompany`;
USE `buscompany`;

####### Tables from accounts ########
CREATE TABLE account
(
    id         INT(11)                  NOT NULL AUTO_INCREMENT,
    login      VARCHAR(50)              NOT NULL,
    password   VARCHAR(50)              NOT NULL,
    first_name VARCHAR(50)              NOT NULL,
    last_name  VARCHAR(50)              NOT NULL,
    patronymic VARCHAR(50)              NULL     DEFAULT NULL,
    user_type  ENUM ('ADMIN', 'CLIENT') NOT NULL DEFAULT 'client',
    PRIMARY KEY (id),
    UNIQUE KEY login (login),
    INDEX (login)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE admin
(
    id         INT(11)     NOT NULL AUTO_INCREMENT,
    account_id INT(11)     NOT NULL,
    position   VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE client
(
    id         INT(11)     NOT NULL AUTO_INCREMENT,
    account_id INT(11)     NOT NULL,
    email      VARCHAR(50) NULL DEFAULT NULL,
    phone      VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE session
(
    id          INT(11)     NOT NULL AUTO_INCREMENT,
    account_id  INT(11)     NOT NULL,
    session_id  VARCHAR(36) NOT NULL,
    last_action DATETIME    NOT NULL,
    PRIMARY KEY (id),
    INDEX (session_id),
    UNIQUE uniq_account (account_id),
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);

####### Tables from tickets ########
CREATE TABLE bus
(
    id          INT(11)     NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL,
    place_count INT(3)      NOT NULL DEFAULT 30,
    PRIMARY KEY (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

#### insert default bus
INSERT INTO bus (name, place_count)
VALUES ('Пазик', 21);
INSERT INTO bus (name, place_count)
VALUES ('Ikarus', 41);

CREATE TABLE station
(
    id   INT(11)     NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    UNIQUE KEY uniq_station (name),
    PRIMARY KEY (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

## insert default station
INSERT INTO station (name) VALUE ('Omsk');
INSERT INTO station (name) VALUE ('Новосибирск');

CREATE TABLE trip
(
    id              INT(11)     NOT NULL AUTO_INCREMENT,
    bus_id          INT(11)     NULL     DEFAULT NULL,
    from_station_id INT(11)     NULL     DEFAULT NULL,
    to_station_id   INT(11)     NULL     DEFAULT NULL,
    start           VARCHAR(30) NOT NULL,
    duration        VARCHAR(30) NOT NULL,
    price           INT(11),
    approved        BOOLEAN     NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (bus_id) REFERENCES bus (id) ON DELETE SET NULL,
    FOREIGN KEY (from_station_id) REFERENCES station (id) ON DELETE SET NULL,
    FOREIGN KEY (to_station_id) REFERENCES station (id) ON DELETE SET NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE schedule
(
    id        INT(11)     NOT NULL AUTO_INCREMENT,
    from_date DATE        NOT NULL,
    to_date   DATE        NOT NULL,
    `period`   VARCHAR(30) NOT NULL,
    trip_id   INT(11)     NOT NULL,
    PRIMARY KEY (id),
    INDEX (from_date),
    INDEX (to_date),
    INDEX (period),
    FOREIGN KEY (trip_id) REFERENCES trip (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE date_trip
(
    id      INT(11) NOT NULL AUTO_INCREMENT,
    trip_id INT(11) NULL DEFAULT NULL,
    date    DATE    NOT NULL,
    PRIMARY KEY (id),
    INDEX (trip_id),
    INDEX (date),
    UNIQUE KEY uniq_date_trip (trip_id, date),
    FOREIGN KEY (trip_id) REFERENCES trip (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE passenger
(
    id         INT(11)     NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL,
    passport   VARCHAR(20) NOT NULL,
    UNIQUE KEY uniq_passenger (first_name, last_name, passport),
    PRIMARY KEY (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE orders
(
    id           INT(11) NOT NULL AUTO_INCREMENT,
    date_trip_id INT(11) NULL DEFAULT NULL,
    client_id    INT(11) NULL DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (date_trip_id) REFERENCES date_trip (id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE order_passenger
(
    id           INT(11) NOT NULL AUTO_INCREMENT,
    order_id     INT(11) NOT NULL,
    passenger_id INT(11) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY order_id_passenger_id (order_id, passenger_id),
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (passenger_id) REFERENCES passenger (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE place
(
    id           INT(11) NOT NULL AUTO_INCREMENT,
    number       INT(3)  NOT NULL,
    date_trip_id INT(11) NOT NULL,
    passenger_id INT(11) NULL DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (date_trip_id) REFERENCES date_trip (id) ON DELETE CASCADE,
    FOREIGN KEY (passenger_id) REFERENCES passenger (id) ON DELETE SET NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

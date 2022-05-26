DROP
    DATABASE IF EXISTS buscompany;
CREATE
    DATABASE `buscompany`;
USE `buscompany`;

####### Tables from accounts ########
CREATE TABLE user_type
(
    id   INT(11)     NOT NULL AUTO_INCREMENT,
    type VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

#default value insert
INSERT INTO user_type (type) VALUE ('admin');
INSERT INTO user_type (type) VALUE ('client');

CREATE TABLE account
(
    id           INT(11)     NOT NULL AUTO_INCREMENT,
    login        VARCHAR(50) NOT NULL,
    password     VARCHAR(50) NOT NULL,
    first_name   VARCHAR(50) NOT NULL,
    last_name    VARCHAR(50) NOT NULL,
    patronymic   VARCHAR(50) NULL DEFAULT NULL,
    email        VARCHAR(50) NULL DEFAULT NULL,
    phone        VARCHAR(50) NULL DEFAULT NULL,
    position     VARCHAR(50) NULL DEFAULT NULL,
    user_type_id INT(11)     NULL DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY login (login),
    INDEX (login),
    FOREIGN KEY (user_type_id) REFERENCES user_type (id) ON DELETE SET NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

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
INSERT INTO bus (name, place_count) VALUES ('Пазик', 21);
INSERT INTO bus (name, place_count) VALUES ('Икарус', 41);

CREATE TABLE station
(
    id   INT(11)     NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    UNIQUE KEY login (name),
    INDEX (name),
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
    from_date VARCHAR(30) NOT NULL,
    to_date   VARCHAR(30) NOT NULL,
    periods   VARCHAR(30) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uniq_schedule (from_date, to_date, periods),
    INDEX (from_date),
    INDEX (to_date),
    INDEX (periods)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE trip_schedule
(
    id          INT(11) NOT NULL AUTO_INCREMENT,
    trip_id     INT(11) NOT NULL,
    schedule_id INT(11) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY trip_id_schedule_id (trip_id, schedule_id),
    FOREIGN KEY (trip_id) REFERENCES trip (id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedule (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE date_trip
(
    id      INT(11)     NOT NULL AUTO_INCREMENT,
    trip_id INT(11)     NULL DEFAULT NULL,
    date    VARCHAR(30) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (trip_id) REFERENCES trip (id) ON DELETE SET NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE orders
(
    id      INT(11)     NOT NULL AUTO_INCREMENT,
    trip_id INT(11)     NULL DEFAULT NULL,
    date    DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id),
    FOREIGN KEY (trip_id) REFERENCES trip (id) ON DELETE SET NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE passenger
(
    id         INT(11)     NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL,
    passport   VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
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

CREATE TABLE ticket
(
    id                 INT(11) NOT NULL AUTO_INCREMENT,
    order_passenger_id INT(11) NULL DEFAULT NULL,
    place              INT(3),
    PRIMARY KEY (id),
    FOREIGN KEY (order_passenger_id) REFERENCES order_passenger (id) ON DELETE SET NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;
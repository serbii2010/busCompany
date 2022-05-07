DROP
    DATABASE IF EXISTS buscompany;
CREATE
    DATABASE `buscompany`;
USE `buscompany`;

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
    login        VARCHAR(45) NOT NULL,
    password     VARCHAR(45) NOT NULL,
    firstName    VARCHAR(45) NOT NULL,
    lastName     VARCHAR(45) NOT NULL,
    patronymic   VARCHAR(45) NULL DEFAULT NULL,
    email        VARCHAR(45) NULL DEFAULT NULL,
    phone        VARCHAR(45) NULL DEFAULT NULL,
    position     VARCHAR(45) NULL DEFAULT NULL,
    user_type_id INT(11)     NULL DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY login (login),
    INDEX (login),
    FOREIGN KEY (user_type_id) REFERENCES user_type (id) ON DELETE SET NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;
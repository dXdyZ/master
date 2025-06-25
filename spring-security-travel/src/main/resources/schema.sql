CREATE SCHEMA IF NOT EXISTS `spring`;


CREATE TABLE IF NOT EXISTS `spring`.`users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `enable` INT NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `spring`.`authorities` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `authority` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

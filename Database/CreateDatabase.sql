-- sudo apt install mysql-server
-- rootpassword: qiprwzhiuhqfwdpih
-- mysql_secure_installation
-- Answer following:
-- enter rootpassword
-- Y
-- 0
-- no
-- Y
-- Y
-- Y
-- Y

-- connect to database with: mysql --user=root --password
-- paste all which is following into the console to create the database
DROP USER 'sec_read'@'localhost';
DROP USER 'sec_write'@'localhost';
FLUSH PRIVILEGES;

CREATE USER 'sec_read'@'localhost' IDENTIFIED BY 'pai1hdsfa!shjASDFfdpasdhf';
CREATE USER 'sec_write'@'localhost' IDENTIFIED BY 'asdhfpoASDF!1dsafhaoidsfj';
GRANT SELECT ON sec.* TO 'sec_read'@'localhost';
GRANT INSERT ON sec.* TO 'sec_write'@'localhost';
FLUSH PRIVILEGES;

CREATE SCHEMA IF NOT EXISTS `sec` DEFAULT CHARACTER SET utf8;
USE `sec`;

DROP TABLE IF EXISTS `sec`.`User_Assurance`;
DROP TABLE IF EXISTS `sec`.`Assurance`;
DROP TABLE IF EXISTS `sec`.`User`;

CREATE TABLE IF NOT EXISTS `sec`.`User` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `Forename` VARCHAR(45) NOT NULL,
    `Surname` VARCHAR(45) NOT NULL,
    `Email` VARCHAR(45) NOT NULL,
    `Birthday` DATE NOT NULL,
    `Salt` VARCHAR(128) NOT NULL,
    `Hash` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `sec`.`Assurance` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `Runtime` INT NOT NULL,
    `Payment_Method` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `sec`.`User_Assurance` (
    `id_User` INT NOT NULL,
    `id_Assurance` INT NOT NULL,
    PRIMARY KEY (`id_User`, `id_Assurance`),
    CONSTRAINT `fk_User_Assurance_1`
        FOREIGN KEY (`id_User`)
        REFERENCES `sec`.`Assurance` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- default password is solchlangepasswörtersindblöde
-- berechnung erfolgt folgerndermassen:  (echo -n "4nXgryJGKZwKvmQRecc9DyfNEbWdEpAjSsdSkhPSS47aewyfKCbeUM999NangwLu9GhTCggc4fATxUzRqXCSUvtj2austS9Abvdg4QLHjSCAnjtG7QsG9X9FXmRTj5dq" && echo -n "solchlangepasswörtersindblöde") | sha256sum
INSERT INTO `sec`.`User` VALUES (
    NULL,
    "Forname1",
    "Surname1",
    "lucker@not-existing.at",
    "1990-10-21",
    "4nXgryJGKZwKvmQRecc9DyfNEbWdEpAjSsdSkhPSS47aewyfKCbeUM999NangwLu9GhTCggc4fATxUzRqXCSUvtj2austS9Abvdg4QLHjSCAnjtG7QsG9X9FXmRTj5dq",
    "14257fabc8d199748b826b4deddec4e139b00595f5ad2751002875cec43258d9"
);


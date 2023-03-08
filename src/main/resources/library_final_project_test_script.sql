-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema library_final_project_test
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema library_final_project_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `library_final_project_test` DEFAULT CHARACTER SET utf8 ;
USE `library_final_project_test` ;

-- -----------------------------------------------------
-- Table `library_final_project_test`.`user_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`user_status` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`user_status` (
  `user_status_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_status_id`),
  CONSTRAINT UC_Name UNIQUE (name));


-- -----------------------------------------------------
-- Table `library_final_project_test`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`role` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  CONSTRAINT UC_Name UNIQUE (name));


-- -----------------------------------------------------
-- Table `library_final_project_test`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`user` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`user` (
  `login` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `role_id` INT NOT NULL,
  `status_id` INT NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NULL,
  PRIMARY KEY (`login`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  INDEX `fk_user_user_status_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_status`
    FOREIGN KEY (`status_id`)
    REFERENCES `library_final_project_test`.`user_status` (`user_status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `library_final_project_test`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `library_final_project_test`.`publication`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`publication` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`publication` (
  `publication_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`publication_id`),
  CONSTRAINT UC_Name UNIQUE (name));


-- -----------------------------------------------------
-- Table `library_final_project_test`.`book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`book` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`book` (
  `isbn` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `date_of_publication` DATE NOT NULL,
  `publication_id` INT NOT NULL,
  `quantity` INT NOT NULL DEFAULT 1,
  `details` VARCHAR(5000) NULL,
  PRIMARY KEY (`isbn`),
  UNIQUE INDEX `isbn_UNIQUE` (`isbn` ASC) VISIBLE,
  INDEX `fk_book_publication1_idx` (`publication_id` ASC) VISIBLE,
  CONSTRAINT `fk_book_publication1`
    FOREIGN KEY (`publication_id`)
    REFERENCES `library_final_project_test`.`publication` (`publication_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `library_final_project_test`.`authors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`authors` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`authors` (
  `authors_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`authors_id`),
  CONSTRAINT UC_Name UNIQUE (name));


-- -----------------------------------------------------
-- Table `library_final_project_test`.`book_has_authors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`book_has_authors` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`book_has_authors` (
  `b_isbn` INT NOT NULL,
  `a_id` INT NOT NULL,
  PRIMARY KEY (`b_isbn`, `a_id`),
  INDEX `fk_book_has_authors_authors1_idx` (`a_id` ASC) VISIBLE,
  INDEX `fk_book_has_authors_book1_idx` (`b_isbn` ASC) VISIBLE,
  CONSTRAINT `fk_book_has_authors_book1`
    FOREIGN KEY (`b_isbn`)
    REFERENCES `library_final_project_test`.`book` (`isbn`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_book_has_authors_authors1`
    FOREIGN KEY (`a_id`)
    REFERENCES `library_final_project_test`.`authors` (`authors_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `library_final_project_test`.`subscription_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`subscription_status` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`subscription_status` (
  `subscription_status_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`subscription_status_id`),
  CONSTRAINT UC_Name UNIQUE (name));


-- -----------------------------------------------------
-- Table `library_final_project_test`.`way_of_using`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`way_of_using` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`way_of_using` (
  `way_of_using_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`way_of_using_id`),
  CONSTRAINT UC_Name UNIQUE (name));


-- -----------------------------------------------------
-- Table `library_final_project_test`.`active_book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`active_book` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`active_book` (
  `active_book_id` INT NOT NULL AUTO_INCREMENT,
  `book_isbn` INT NOT NULL,
  `way_of_using_id` INT NOT NULL,
  `subscription_status_id` INT NOT NULL,
  `start_date` DATETIME NOT NULL DEFAULT now(),
  `end_date` DATETIME NOT NULL DEFAULT (date_format(`start_date`,'%y-%m-%d 18-00-00')),
  `fine` DECIMAL(9,2) NULL,
  PRIMARY KEY (`active_book_id`),
  UNIQUE INDEX `active_book_id_UNIQUE` (`active_book_id` ASC) VISIBLE,
  INDEX `fk_active_book_subscription_status1_idx` (`subscription_status_id` ASC) VISIBLE,
  INDEX `fk_active_book_way_of_using1_idx` (`way_of_using_id` ASC) VISIBLE,
  INDEX `fk_active_book_book1_idx` (`book_isbn` ASC) VISIBLE,
  CONSTRAINT `fk_active_book_subscription_status1`
    FOREIGN KEY (`subscription_status_id`)
    REFERENCES `library_final_project_test`.`subscription_status` (`subscription_status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_active_book_way_of_using1`
    FOREIGN KEY (`way_of_using_id`)
    REFERENCES `library_final_project_test`.`way_of_using` (`way_of_using_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_active_book_book1`
    FOREIGN KEY (`book_isbn`)
    REFERENCES `library_final_project_test`.`book` (`isbn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `library_final_project_test`.`active_book_has_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_final_project_test`.`active_book_has_user` ;

CREATE TABLE IF NOT EXISTS `library_final_project_test`.`active_book_has_user` (
  `active_book_id` INT NOT NULL,
  `user_login` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`active_book_id`, `user_login`),
  INDEX `fk_active_book_has_user_user1_idx` (`user_login` ASC) VISIBLE,
  INDEX `fk_active_book_has_user_active_book1_idx` (`active_book_id` ASC) VISIBLE,
  CONSTRAINT `fk_active_book_has_user_active_book1`
    FOREIGN KEY (`active_book_id`)
    REFERENCES `library_final_project_test`.`active_book` (`active_book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_active_book_has_user_user1`
    FOREIGN KEY (`user_login`)
    REFERENCES `library_final_project_test`.`user` (`login`) ON DELETE CASCADE ON UPDATE CASCADE);
    
    
-- alter table publication
-- ADD CONSTRAINT UC_Name UNIQUE (name);

-- alter table authors
-- ADD CONSTRAINT UC_Name UNIQUE (name);

-- alter table role
-- ADD CONSTRAINT UC_Name UNIQUE (name);

-- alter table subscription_status
-- ADD CONSTRAINT UC_Name UNIQUE (name);

-- alter table user_status
-- ADD CONSTRAINT UC_Name UNIQUE (name);

-- alter table way_of_using
-- ADD CONSTRAINT UC_Name UNIQUE (name);


INSERT INTO `role` (`name`)
values('reader'),
('librarian'),
('administrator');

INSERT INTO user_status(name)
VALUES
('blocked'),
('active');

INSERT INTO way_of_using(name)
VALUES
('subscription'),
('reading room');

INSERT INTO subscription_status(name)
VALUES
('active'),
('returned'),
('fined'),
('waiting');

Insert into book values
(1,'book1','11-11-11',1,1,null),
(2,'book2','11-11-11',1,2,null);

insert into publication values
(default,'publication1');

insert into authors 
values
(default,'author1'),
(default,'author2');

insert into book_has_authors values
(1,2),
(1,1),
(2,2);

Insert into user values
('user1', 'asd','asdwe',1,1,'Ostap','Patso',default);

Insert into user values
('userLibr1', 'asd','asdwe',2,1,'lib','miy',default);

Insert into user values
('userLibr2', 'asd','asdwe',2,1,'lib','nemiy',default);

Insert Into active_book values
(default,1,1,1,'2022-11-20','2022-12-15',null);

Insert Into active_book values
(default,2,1,1,'2022-11-20','2022-12-15',null);

insert into active_book_has_user
values
(2,'userLibr2');

insert into active_book_has_user values
(1,'user1'),
(1,'userLibr1'),
(2,'user1');

Insert into user values
('userLib21', 'asd',md5('asdwe'),2,1,'lib','miy',default);

SET FOREIGN_KEY_CHECKS=1;













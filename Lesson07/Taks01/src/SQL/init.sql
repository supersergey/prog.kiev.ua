CREATE TABLE Clients (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
group_id INT DEFAULT NULL,
address_id INT DEFAULT NULL,
name VARCHAR(128) DEFAULT NULL,
surname VARCHAR(128) DEFAULT NULL,
email VARCHAR(128) DEFAULT NULL,
phone VARCHAR(12) NOT NULL,
card_number VARCHAR(12) DEFAULT NULL,
discriminator VARCHAR(1) NOT NULL
);

CREATE TABLE Groups (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(128) NOT NULL,
comment TEXT DEFAULT NULL,
date DATE DEFAULT NULL
);

CREATE TABLE Addresses (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
country VARCHAR(50) NOT NULL,
city VARCHAR(128) NOT NULL,
house INT NOT NULL
);

CREATE TABLE Courses (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name TEXT DEFAULT NULL
);

CREATE TABLE Client_Course (
client_id INT NOT NULL,
course_id INT NOT NULL
);

create table menu (
  id int auto_increment not null primary key,
  name varchar(100) default null,
  price int default null,
  weight float default null,
  discount bool default null);
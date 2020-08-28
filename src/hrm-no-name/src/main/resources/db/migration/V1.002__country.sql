DROP TABLE IF EXISTS country;
CREATE TABLE country (
  id bigint NOT NULL AUTO_INCREMENT,
  country_name varchar(50) DEFAULT NULL,
  country_code varchar(20) DEFAULT NULL,
  PRIMARY KEY (id)
);
insert into country(id,country_name,country_code) values (4,'Việt Nam','vi'),(2,'English','en'),(3,'Japan','ja');
DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  phone varchar(10) DEFAULT NULL,
  email varchar(99) NOT NULL,
  password varchar(255) NOT NULL,
  role_id smallint DEFAULT NULL,
  start_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  end_date timestamp NULL DEFAULT NULL,
  token varchar(512) DEFAULT NULL,
  token_start timestamp NULL DEFAULT NULL,
  token_end timestamp NULL DEFAULT NULL,
  code varchar(6) DEFAULT NULL,
  code_start timestamp NULL DEFAULT NULL,
  code_end timestamp NULL DEFAULT NULL,
  display_order int NULL DEFAULT '0',
  delete_flag tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Delete status is 1',
  status tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (id)
);

/*Data for the table user */

insert into user(id,name,phone,email,password,role_id,start_date,end_date,token,token_start,token_end,code,code_start,code_end,display_order,delete_flag,status) values (325,'Mac Van Huong','0932216616','guest@gmail.com','$2a$12$uSvrU8MThhOG5uaZtph3JOnP4xRHqWlA2AZhoZdALSHNSZ538ydIS',3,'2020-07-29 16:38:54',NULL,NULL,'2020-03-10 00:00:00','2020-04-10 00:00:00',NULL,NULL,NULL,0,0,1),(326,'admin','1234567','admin@gmail.com','$2a$12$uSvrU8MThhOG5uaZtph3JOnP4xRHqWlA2AZhoZdALSHNSZ538ydIS',2,'2020-07-23 15:05:50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,1),(327,'member','1234567','member@gmail.com','$2a$12$uSvrU8MThhOG5uaZtph3JOnP4xRHqWlA2AZhoZdALSHNSZ538ydIS',1,'2020-07-23 15:05:50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,1);

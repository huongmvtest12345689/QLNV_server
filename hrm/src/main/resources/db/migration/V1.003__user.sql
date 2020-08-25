DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  phone varchar(10) DEFAULT NULL,
  email varchar(99) NOT NULL,
  password varchar(50) NOT NULL,
  roles_id smallint DEFAULT NULL,
  permission_id smallint DEFAULT NULL,
  start_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  end_date timestamp NULL DEFAULT NULL,
  token varchar(512) DEFAULT NULL,
  token_start timestamp NULL DEFAULT NULL,
  token_end timestamp NULL DEFAULT NULL,
  code varchar(6) DEFAULT NULL,
  code_start timestamp NULL DEFAULT NULL,
  code_end timestamp NULL DEFAULT NULL,
  display_order int NOT NULL,
  delete_flag tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Delete status is 1',
  status tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (id)
);

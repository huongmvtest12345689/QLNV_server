DROP TABLE IF EXISTS permissions_modules;
CREATE TABLE permissions_modules (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  module_key varchar(255) DEFAULT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
  deleted_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
insert into permissions_modules(id, name, module_key) values(1,'user list', '/admin/userList');
insert into permissions_modules(id, name, module_key) values(2,'utilities permission','/admin/permissions');
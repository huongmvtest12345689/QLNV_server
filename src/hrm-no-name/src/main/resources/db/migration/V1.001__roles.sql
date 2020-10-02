DROP TABLE IF EXISTS roles;
create table roles (
	id int,
	role_id int,
	role_name varchar (300),
	status tinyint,
	permission_ids varchar (255) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp NULL DEFAULT NULL,
	PRIMARY KEY (id)
); 
insert into roles(id, role_id, role_name, status, permission_ids) values(1,1,'ROLES_MEMBER',1,'2,4,');
insert into roles(id, role_id, role_name, status,permission_ids) values(2,2,'ROLES_ADMIN',1,'1,2,3,4,5,6,7');
insert into roles(id, role_id, role_name, status, permission_ids) values(3,3,'ROLES_GUEST',1,'4');

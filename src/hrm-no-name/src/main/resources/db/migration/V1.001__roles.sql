DROP TABLE IF EXISTS roles;
create table roles (
	id int,
	roles_id int,
	roles_name varchar (300),
	status tinyint,
	PRIMARY KEY (id)
); 
insert into roles(id, roles_id, roles_name, status) values(1,1,'ROLES_MEMBER',1);
insert into roles(id, roles_id, roles_name, status) values(2,2,'ROLES_ADMIN',1);
insert into roles(id, roles_id, roles_name, status) values(3,3,'ROLES_GUEST',1);

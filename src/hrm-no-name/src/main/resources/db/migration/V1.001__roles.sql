DROP TABLE IF EXISTS roles;
create table roles (
	id int,
	roles_id int,
	roles_name varchar (300),
	status tinyint
); 
insert into roles(id, roles_id, roles_name, status) values(1,1,'ROLES_MEMBER',1);
insert into roles(id, roles_id, roles_name, status) values(2,2,'ROLES_ADMIN',1);
insert into roles(id, roles_id, roles_name, status) values(3,9,'ROLES_MASTER',1);
insert into roles(id, roles_id, roles_name, status) values(4,3,'ROLES_PRINCESS',1);
insert into roles(id, roles_id, roles_name, status) values(5,3,'ROLES_KINGS',1);

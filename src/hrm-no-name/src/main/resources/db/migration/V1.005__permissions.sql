DROP TABLE IF EXISTS permissions;
CREATE TABLE permissions (
  id bigint NOT NULL AUTO_INCREMENT,
  permissions_modules_id bigint NOT NULL,
  name varchar(255) DEFAULT NULL,
  permissions_key varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
  deleted_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
insert into permissions(id, permissions_modules_id, name, permissions_key, description) values(1, 1, 'Xóa tài khoản', 'delete-list-user', 'Chỉ có ADMIN mới có quyền xóa tài khoản!');
insert into permissions(id, permissions_modules_id, name, permissions_key, description) values(2, 1, 'Sửa tài khoản', 'edit-list-user', 'Chỉ có ADMIN mới có quyền sửa tất cả, còn MEMBER chỉ có thể sửa chính mình');
insert into permissions(id, permissions_modules_id, name, permissions_key, description) values(3, 1, 'Thêm tài khoản', 'create-list-user', 'Chỉ có ADMIN mới có quyền thêm tài khoản!');
insert into permissions(id, permissions_modules_id, name, permissions_key, description) values(4, 1, 'Xem tài khoản', 'view-list-user', 'Tất cả đêu có quyền xem!');
insert into permissions(id, permissions_modules_id, name, permissions_key, description) values(5, 2, 'Xem trang phân quyền', 'view-permissions', 'Chỉ ADMIN có quyền xem ở trang này');
insert into permissions(id, permissions_modules_id, name, permissions_key, description) values(6, 2, 'Sửa trang phân quyền', 'edit-permissions', 'Chỉ ADMIN có quyền sửa ở trang này');
insert into permissions(id, permissions_modules_id, name, permissions_key, description) values(7, 2, 'Thêm trang phân quyền', 'add-permissions', 'Chỉ ADMIN có quyền thêm ở trang này');
update
    user u
set u.name = :name, u.role_id = :roleId, u.phone = :phone
WHERE u.email = :email
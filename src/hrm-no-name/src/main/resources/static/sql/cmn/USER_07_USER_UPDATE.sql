update
    user u
set u.name = :name, u.roles_id = :roleId, u.phone = :phone
WHERE u.email = :email
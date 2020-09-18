update
    user u
set u.password = :password, u.code_end = :code_end, u.code_start = :code_start
WHERE u.id = :id
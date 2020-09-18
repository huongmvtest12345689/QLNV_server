update
    user u
set u.code = :code, u.code_start = :code_start, u.code_end = :code_end
WHERE u.id = :id
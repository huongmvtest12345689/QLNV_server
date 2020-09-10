SELECT u.id,
	u.name,
	u.phone,
    u.email,
    u.password,
    u.roles_id,
    u.status,
    u.code,
    u.code_end,
    u.code_start,
    u.delete_flag,
    u.display_order,
    u.start_date,
    u.end_date,
    u.token,
    u.token_start,
    u.token_end
FROM user u


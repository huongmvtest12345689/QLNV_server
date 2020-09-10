SELECT 
    c.id,
    c.country_name,
    c.country_code
FROM country c
WHERE email = {a}
ORDER BY c.id ASC
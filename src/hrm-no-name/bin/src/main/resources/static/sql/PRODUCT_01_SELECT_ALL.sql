SELECT
   p.id,
   p.name,
   p.content,
   p.start_date,
   p.end_date,
   p.create_date,
   p.update_date,
   p.delete_date,
   p.status,
   p.delete_flag,
   p.display_order
FROM
   Product p
WHERE
   p.delete_flag = 0
ORDER BY
   display_order DESC
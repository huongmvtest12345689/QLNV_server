UPDATE Product 
SET 
	delete_date = ?, 
	delete_flag = 1
WHERE 
	id IN (:strId)
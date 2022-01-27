CREATE OR REPLACE FUNCTION SHOW_MTLS_LESS_THAN(price int)
returns TABLE(mtl_id int, mtl_name varchar(50), mtl_uom varchar(5), mtl_price DECIMAL(10, 2))
as $$
BEGIN
    RETURN query SELECT * FROM materials 
    WHERE materials.mtl_price < price;
END
$$ LANGUAGE plpgsql;

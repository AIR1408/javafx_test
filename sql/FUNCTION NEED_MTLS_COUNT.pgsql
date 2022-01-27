CREATE or replace FUNCTION NEED_MTLS_COUNT(detail_id int)
RETURNS integer as $$
BEGIN
    RETURN (SELECT count(det_id) FROM mtl_norms
    WHERE det_id = detail_id);
END
$$ LANGUAGE plpgsql;

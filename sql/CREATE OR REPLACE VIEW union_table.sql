CREATE OR REPLACE VIEW union_table AS
SELECT ln.det_id, ln.op_id, materials.mtl_id, mtl_name, 
    mtl_uom, mtl_price, norm, prof_id, 
    prof_level, tarif_id, pre_post_time, time_per_piece
FROM materials
RIGHT JOIN mtl_norms mn ON materials.mtl_id = mn.mtl_id 
LEFT JOIN labor_norms ln ON mn.det_id = ln.det_id AND mn.op_id = ln.op_id;

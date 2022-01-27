DELETE FROM mtl_norms;
DELETE FROM materials;
DELETE FROM labor_norms;


INSERT INTO materials
VALUES (1, 'Материал 1', 'шт', 30),
    (2, 'Материал 2', 'шт', 55),
    (3, 'Материал 3', 'кг', 10),
    (4, 'Материал 4', 'шт', 70),
    (5, 'Материал 5', 'кг', 105),
    (6, 'Материал 6', 'шт', 145);

INSERT INTO labor_norms
VALUES (1, 1, 1, 2, 1, 5, 6),
    (1, 2, 2, 1, 2, 3, 6),
    (2, 3, 3, 3, 3, 4, 7),
    (3, 5, 5, 2, 4, 8, 7),
    (3, 6, 6, 2, 5, 2, 7),
    (4, 7, 4, 1, 6, 10, 5),
    (5, 3, 7, 1, 7, 12, 7);

INSERT INTO mtl_norms
VALUES (1, 2, 2, 'шт', 3),
    (1, 1, 1, 'шт', 10),
    (2, 2, 3, 'шт', 5),
    (3, 4, 6, 'шт', 15),
    (4, 5, 7, 'кг', 22),
    (4, 6, 7, 'кг', 30),
    (3, 3, 5, 'кг', 11),
    (5, 6, 3, 'шт', 27),
    (5, 5, 3, 'шт', 33);

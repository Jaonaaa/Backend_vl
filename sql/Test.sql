DROP TABLE test;
CREATE TABLE test (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
DROP TABLE car;
SELECT *
FROM car;
SELECT *
FROM test;
INSERT INTO test (id, name)
VALUES (1, 'name:character varying');
INSERT INTO test (id, name)
VALUES (2, 'name:character car');
INSERT INTO test (id, name)
VALUES (3, 'name:character popo');
INSERT INTO test (id, name)
VALUES (4, 'name:character test');
INSERT INTO test (id, name)
VALUES (5, 'name:character say');
----
---
SELECT *
FROM test
WHERE name LIKE '%car%'
    AND name LIKE '%name:character%'
UNION
SELECT *
FROM test
WHERE name LIKE '%popo%';
SELECT EXTRACT(
        MONTH
        FROM presale_period
    )
FROM car;
INSERT INTO car (
        id,
        imma,
        presale_period,
        published_on,
        color
    )
VALUES (
        999999,
        '978-9730228236',
        '0 years 0 mons 297 days 0 hours 0 mins 0.00 secs',
        '2016-10-01',
        'HRed'
    )
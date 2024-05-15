ALTER TABLE distributors
ADD COLUMN address VARCHAR(30);
---
ALTER TABLE distributors
    RENAME COLUMN address TO city;
----
--To rename an existing table:
ALTER TABLE distributors
    RENAME TO suppliers;
---
----
-- DISABLE
-- Disable all triggers
ALTER TABLE table_name DISABLE TRIGGER ALL;
-- Disable all foreign key constraints
SET session_replication_role = replica;
ALTER TABLE table_name DISABLE TRIGGER ALL;
-- Disable all check constraints
ALTER TABLE table_name DROP CONSTRAINT IF EXISTS constraint_name;
-- Disable all unique constraints
ALTER TABLE table_name DROP CONSTRAINT IF EXISTS constraint_name;
-- Disable all primary key constraints
ALTER TABLE table_name DROP CONSTRAINT IF EXISTS constraint_name;
----
---
-- ENABLE 
-- Re-enable all triggers
ALTER TABLE table_name ENABLE TRIGGER ALL;
-- Re-enable all foreign key constraints
SET session_replication_role = DEFAULT;
ALTER TABLE table_name ENABLE TRIGGER ALL;
-- Re-enable all check constraints (if you dropped them)
ALTER TABLE table_name
ADD CONSTRAINT constraint_name CHECK (condition);
-- Re-enable all unique constraints (if you dropped them)
ALTER TABLE table_name
ADD CONSTRAINT constraint_name UNIQUE (columns);
-- Re-enable all primary key constraints (if you dropped them)
ALTER TABLE table_name
ADD CONSTRAINT constraint_name PRIMARY KEY (columns);
SELECT id,
    begin_date,
    total_price
FROM devis;
SELECT SUM(total_price) price_total,
    EXTRACT(
        MONTH
        FROM begin_date
    ) as time_label
FROM devis
GROUP BY time_label
ORDER BY time_label ASC;
SELECT SUM(total_price) price_total,
    EXTRACT(
        YEAR
        FROM begin_date
    ) as time_label
FROM devis
GROUP BY time_label;
CREATE TABLE test (d date);
INSERT INTO test
VALUES ('2024-01-05');
INSERT INTO test
VALUES ('2022-01-05');
INSERT INTO test
VALUES ('2024-01-05');
INSERT INTO test
VALUES ('2023-01-05');
SELECT DISTINCT(
        EXTRACT(
            YEAR
            FROM d
        )
    ) as time_label
FROM test
GROUP BY time_label
ORDER BY time_label DESC;
SELECT *
FROM test;
DROP TABLE test;
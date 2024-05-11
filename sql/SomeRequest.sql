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
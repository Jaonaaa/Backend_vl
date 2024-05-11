SELECT *
FROM car;
DROP TABLE red_only;
SELECT *
FROM red_only;
CREATE OR REPLACE VIEW red_only AS
SELECT *
FROM car
WHERE color = 'Red';
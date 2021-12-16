-- 7.1
-- 1
SELECT s.INSTNM,r.RELAFFIL_VALUE 
FROM school s 
JOIN relaffil r
ON s.RELAFFIL = r.RELAFFIL_ID
WHERE s.RELAFFIL IS NOT NULL
LIMIT 10;
-- 2
SELECT s.INSTNM,r.RELAFFIL_VALUE 
FROM school s 
JOIN relaffil r
ON s.RELAFFIL = r.RELAFFIL_ID
LIMIT 10;
-- 3
SELECT s.INSTNM,r.REGION_VALUE,loc.LATITUDE,loc.LONGITUDE
FROM school s 
	JOIN region r ON s.LOCALE = 11
    JOIN root loc ON s.school_ID = loc.root_ID
LIMIT 10;
-- 4
SELECT s.INSTNM,a.SAT_AVG
FROM school s 
	JOIN admissions a 
    ON s.school_ID = a.admissions_ID
WHERE a.SAT_AVG IS NOT NULL
ORDER BY a.ADM_RATE
LIMIT 20;
-- 5
SELECT s.INSTNM
FROM school s 
	JOIN admissions a 
	ON s.school_ID = a.admissions_ID
ORDER BY a.SAT_AVG DESC
LIMIT 20;
-- 6
SELECT SAT_AVG FROM admissions
UNION
SELECT ADM_RATE FROM admissions
ORDER BY SAT_AVG DESC
LIMIT 30;

-- 7.2
-- 1
SELECT INSTNM FROM school
WHERE LOCALE IN (SELECT LOCALE_ID 
				 FROM locale 
                 WHERE LOCALE_VALUE LIKE "City%")
LIMIT 10;
-- 2
SELECT INSTNM FROM school
WHERE REGION IN (SELECT REGION_ID 
				 FROM region
                 WHERE REGION_VALUE LIKE "New England%"
                 OR REGION_VALUE LIKE "Mid East%")
LIMIT 10;
-- 3
SELECT INSTNM,REGION = (SELECT REGION_ID 
				 FROM region
				 WHERE REGION_VALUE LIKE "New England%")
FROM school
LIMIT 10;

-- 7.3
-- 1
-- 2
-- 3
-- 4
-- 5
-- 6

-- 7.4
-- 1
CREATE VIEW RELGIOUS_AFFIL AS
	SELECT s.INSTNM,r.RELAFFIL_VALUE 
	FROM school s 
	JOIN relaffil r
	ON s.RELAFFIL = r.RELAFFIL_ID
	LIMIT 10;
-- 2
CREATE VIEW TOP20 AS
	SELECT s.INSTNM
	FROM school s 
		JOIN admissions a 
		ON s.school_ID = a.admissions_ID
	ORDER BY a.SAT_AVG DESC
	LIMIT 20;

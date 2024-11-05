--1: Найти имена сотрудников, получивших за годы начисления зарплаты минимальную зарплату.
SELECT e.empname FROM EMP e NATURAL JOIN SALARY s
WHERE  s.SALVALUE = (SELECT min(s.salvalue) FROM SALARY s)

--2: Найти имена сотрудников, работавших или работающих в тех же отделах, в которых работал или работает сотрудник с именем RICHARD MARTIN.
SELECT e.empname FROM EMP e NATURAL JOIN CAREER c
WHERE e.empname != 'RICHARD MARTIN' 
AND c.deptno in (SELECT c.deptno FROM CAREER c NATURAL JOIN EMP e where e.empname = 'RICHARD MARTIN')
GROUP BY e.empname;

--3: Найти имена сотрудников, работавших или работающих в тех же отделах и должностях, что и сотрудник 'RICHARD MARTIN'.
SELECT e.empname FROM EMP e NATURAL JOIN CAREER c
WHERE (c.deptno, c.jobno) in (SELECT c.deptno, c.jobno FROM CAREER c
					NATURAL JOIN EMP e WHERE e.empname = 'RICHARD MARTIN')
AND e.empname != 'RICHARD MARTIN';

--4: Найти сведения о номерах сотрудников, получивших за какой-либо месяц зарплату большую, чем средняя зарплата   за 2007 г. или большую чем средняя зарплата за 2008г.
SELECT empno FROM salary
WHERE salvalue > ANY((SELECT AVG(salvalue) FROM salary WHERE year = 2007), (SELECT AVG(salvalue) FROM salary WHERE year = 2008))
GROUP BY empno;


--5: Найти сведения о номерах сотрудников, получивших зарплату за какой-либо месяц большую, чем средние зарплаты за все годы начислений.
SELECT empno FROM salary 
WHERE salvalue > ALL(SELECT AVG(salvalue) FROM salary GROUP BY year) 
GROUP BY empno;

--6: Определить годы, в которые начисленная средняя зарплата была больше средней зарплаты за все годы начислений.
SELECT year FROM SALARY GROUP BY year
HAVING(AVG(salvalue)) > (SELECT AVG(salvalue) FROM salary)

--7: Определить номера отделов, в которых работали или работают сотрудники, имеющие начисления зарплаты. 
SELECT deptno FROM DEPT d 
WHERE deptno IN ( 
	SELECT deptno FROM CAREER c
	NATURAL JOIN EMP e
	NATURAL JOIN SALARY s
	WHERE s.salvalue IS NOT NULL);

--8: Определить номера отделов, в которых работали или работают сотрудники, имеющие начисления зарплаты.
SELECT deptno FROM DEPT d 
WHERE EXISTS(
	SELECT deptno FROM CAREER c
	NATURAL JOIN EMP e
	NATURAL JOIN SALARY s
	WHERE d.deptno = c.deptno);

--9: Определить номера отделов, для сотрудников которых не начислялась зарплата.
SELECT deptno FROM DEPT d 
WHERE NOT EXISTS (
	SELECT deptno FROM CAREER c
	NATURAL JOIN EMP e
	NATURAL JOIN SALARY s
	WHERE d.deptno = c.deptno);

--10: Вывести сведения о карьере сотрудников с указанием названий и адресов отделов вместо номеров отделов.
SELECT e.empname, d.deptname, d.deptaddr, c.startdate, c.enddate FROM EMP e
NATURAL JOIN CAREER c
NATURAL JOIN DEPT d

--11: Определить целую часть средних зарплат,  по годам начисления.
SELECT year, CAST(AVG(salvalue) as int) avg_int_salvalue FROM SALARY 
GROUP BY year

--12: Разделите сотрудников на возрастные группы: A) возраст 20-30 лет; B) 31-40 лет; C) 41-50;    D) 51-60 или возраст не определён.
SELECT empname,
CASE 
	WHEN MONTHS_BETWEEN(SYSDATE, birthdate) / 12 >= 20 and MONTHS_BETWEEN(SYSDATE, birthdate) / 12 <= 30 
		THEN 'ГРУППА А'
	WHEN MONTHS_BETWEEN(SYSDATE, birthdate) / 12 >= 31 and MONTHS_BETWEEN(SYSDATE, birthdate) / 12 <= 40
		THEN 'ГРУППА B'
	WHEN MONTHS_BETWEEN(SYSDATE, birthdate) / 12 >= 41 and MONTHS_BETWEEN(SYSDATE, birthdate) / 12 <= 50 
		THEN 'ГРУППА С'
	WHEN MONTHS_BETWEEN(SYSDATE, birthdate) / 12 >= 51 and MONTHS_BETWEEN(SYSDATE, birthdate) / 12 <= 60 
		THEN 'ГРУППA D'
	ELSE 'ГРУППА НЕ ОПРЕДЕЛЕНА' 
END AGE_GROUP
FROM EMP;

--13: Перекодируйте номера отделов, добавив перед номером отдела буквы BI для номеров <=20,  буквы  LN для номеров >=30.
SELECT 
CASE 
	WHEN deptno <= 20 
		THEN CONCAT('BI', CAST(deptno as VARCHAR(10)))
	WHEN deptno >= 30 
		THEN CONCAT('LN', CAST(deptno as VARCHAR(10)))
END deptno_with_prefix
FROM DEPT;

--14: Выдать информацию о сотрудниках из таблицы EMP, заменив отсутствие данного о дате рождения  датой '01-01-1000'.
SELECT empname, empno, COALESCE(birthdate, TO_DATE('01-01-1000', 'dd-mm-yyyy')) BIRTHDATE FROM EMP;
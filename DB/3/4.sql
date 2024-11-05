-- 1) Требуется используя значения столбца START_DATE получить дату за десять дней до и после приема на работу, полгода до и после приема на работу, год до и после приема на работу сотрудника JOHN KLINTON.
SELECT STARTDATE, ENDDATE, (STARTDATE - 10) BEFORE_10_DAYS, (ENDDATE + 10) AFTER_10_DAYS,
ADD_MONTHS(STARTDATE, -6) BEFORE_6_MONTHS, ADD_MONTHS(ENDDATE, 6) AFTER_6_MONTHS, ADD_MONTHS(STARTDATE, -12) BEFORE_12_MONTHS, 
ADD_MONTHS(ENDDATE, 12) AFTER_12_MONTHS
FROM CAREER 
WHERE EMPNO = (SELECT EMPNO FROM EMP WHERE EMPNAME = 'JOHN KLINTON');

-- 2) Требуется найти разность между двумя датами и представить результат в днях. Вычислите разницу в днях между датами приема на работу сотрудников JOHN MARTIN и ALEX BOUSH.
SELECT 
((SELECT STARTDATE FROM CAREER 
         WHERE EMPNO = 
         (SELECT EMPNO FROM EMP WHERE EMPNAME = 'ALEX BOUSH')) - 
              (SELECT STARTDATE FROM CAREER WHERE EMPNO = 
                     (SELECT EMPNO FROM EMP WHERE EMPNAME = 'JOHN MARTIN'))) DAYS_BETWEEN
FROM DUAL;

-- 3) Требуется найти разность между двумя датами в месяцах и в годах.
SELECT 
MONTHS_BETWEEN(SYSDATE, TO_DATE('09-25-1998', 'MM-DD-YYYY')) MONTHS_BETWEEN, 
(MONTHS_BETWEEN(SYSDATE, TO_DATE('09-25-1998', 'MM-DD-YYYY')) / 12) YEARS_BETWEEN 
FROM DUAL;

-- 4) Требуется определить интервал времени в днях между двумя датами. Для каждого сотрудника 20-го отделе найти сколько дней прошло между датой его приема на работу и датой приема на работу следующего сотрудника.
SELECT STARTDATE, 
      ((LEAD(STARTDATE, 1) OVER (ORDER BY STARTDATE)) - STARTDATE) DAYS_BETWEEN 
FROM CAREER 
WHERE DEPTNO = '20';

-- 5) Требуется подсчитать количество дней в году по столбцу START_DATE.
SELECT EXTRACT(YEAR FROM STARTDATE) YEAR, (ADD_MONTHS(TRUNC(STARTDATE, 'y'), 12) - TRUNC(STARTDATE, 'y')) DAYS
FROM CAREER;

-- 6) Требуется разложить текущую дату на день, месяц, год, секунды, минуты, часы. Результаты вернуть в численном виде.
SELECT TO_CHAR(SYSDATE, 'DD.MM.YYYY HH24:MI:SS') AS DATE_CHAR, 
       TO_NUMBER(TO_CHAR(SYSDATE, 'DDMMYYYYHH24MISS')) AS DATE_NUMBER 
FROM DUAL;

-- 7) Требуется получить первый и последний дни текущего месяца.
SELECT TRUNC(LAST_DAY(SYSDATE) - 1, 'mm') AS FIRST_DAY, SYSDATE, LAST_DAY(SYSDATE) LAST_DAY FROM DUAL;

-- 8) Требуется возвратить даты начала и конца каждого из четырех кварталов данного года.
SELECT LEVEL AS QUARTER,
       ADD_MONTHS(trunc(sysdate, 'YEAR'), (LEVEL - 1) * 3) AS QUARTER_START,
       ADD_MONTHS(trunc(sysdate, 'YEAR'), LEVEL * 3) - 1 AS QUARTER_END
FROM DUAL 
CONNECT BY LEVEL <= 4;

-- 9) Требуется найти все даты года, соответствующие заданному дню недели. Сформируйте список понедельников текущего года.
SELECT * FROM 
(SELECT (trunc(sysdate, 'YEAR') + LEVEL - 1) DAY FROM DUAL CONNECT BY LEVEL <= 366) 
WHERE TO_CHAR(DAY, 'fmday') = 'monday';

-- 10) Требуется создать календарь на текущий месяц. Календарь должен иметь семь столбцов в ширину и пять строк вниз.
-- 10) Требуется создать календарь на текущий месяц. Календарь должен иметь семь столбцов в ширину и пять строк вниз.
WITH X AS (SELECT * FROM (SELECT TRUNC(SYSDATE, 'MM') + LEVEL - 1                          MONTH_DATE,
                                 TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'IW')           WEEK_NUMBER,
                                 TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'DD')           DAY_NUMBER,
                                 TO_NUMBER(TO_CHAR(TRUNC(SYSDATE, 'MM')+ LEVEL - 1, 'D'))  WEEK_DAY,
                                 TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'MM')           CURR_MONTH,
                                 TO_CHAR(SYSDATE, 'MM')                                    MONTH
                          FROM DUAL
                          CONNECT BY LEVEL <=31)      
            WHERE CURR_MONTH = MONTH)

SELECT MAX(CASE WEEK_DAY WHEN 2 THEN DAY_NUMBER END) MONDAY,
       MAX(CASE WEEK_DAY WHEN 3 THEN DAY_NUMBER END) TUESDAY,
       MAX(CASE WEEK_DAY WHEN 4 THEN DAY_NUMBER END) WEDNESDAY,
       MAX(CASE WEEK_DAY WHEN 5 THEN DAY_NUMBER END) THURSDAY,
       MAX(CASE WEEK_DAY WHEN 6 THEN DAY_NUMBER END) FRIDAY,
       MAX(CASE WEEK_DAY WHEN 7 THEN DAY_NUMBER END) SATURDAY,
       MAX(CASE WEEK_DAY WHEN 1 THEN DAY_NUMBER END) SUNDAY
FROM X
GROUP BY WEEK_NUMBER
ORDER BY WEEK_NUMBER;
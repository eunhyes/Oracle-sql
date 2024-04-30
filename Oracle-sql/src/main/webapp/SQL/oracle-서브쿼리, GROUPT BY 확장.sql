
/* 반정규화 */
-- 데이터베이스의 성능 향상을 위하여 데이터 중복을 허용하고 조인을 줄이는 방법
-- 테이블 설계를 인위적으로 논리적이지 못하게 만듦
 
-- sal*12 연봉과 같은 계산이 복잡해지면 select 속도가 느려짐
select ename, sal, sal*12 연봉
from emp; 

update emp 
set year_sal = (sal*12) + navl(comm, 0) * 12;


select * from emp;
-- 연봉을 구하기 위한 계산식 필요 X -> select 연산 속도 증가 -> 계산 중복 데이터 -> 반정규화
-- 반정규화 select 속도는 빨라졌지만 insert 시 sal 과 comm 을 계산한 결과값이 입력되어야 함. -> insert 속도 느려짐


-- 컬럼 반정규화 예시
-- 1) 이력테이블(카드 사용금액 저장)	
-- 2) 계산 속성(sum()에 총 카드 사용액 집계)

create table card_history(
	
	card_date date,
	money number,
	total number	-- 이력 + 계산 -> 반정규화 -> 이상현상 : 어제 금액 수정 후 total컬럼 수정해야됨
);


-- total을 조회하고 싶으면
select total from card_history;

-- 반정규화하지 않았다면 -> 집계 연산을 하면 속도가 느려짐
select sum(money) from card_history;



/* 집합연산자 */

create table 친구(name varchar2(50));
create table 직장동료(name varchar2(50));

insert into 친구 values('루피');
insert into 친구 values('조로');
insert into 친구 values('상디');

insert into 직장동료 values('루피');
insert into 직장동료 values('나미');
commit;

-- 친구동료 모두 합집합
select name from 친구
union all
select name from 직장동료; -- 1)번 2)번 결과셋의 열모양(개수, 자료형)이 같아야 한다.

-- 친구동료 중복제거 합집합
select name from 친구
union -- 두결과셋을 비교후 중복값을 제거한다
select name from 직장동료;

-- 차집합
select name from 친구
minus
select name from 직장동료;

-- 교집합
select name from 친구
intersect
select name from 직장동료;


-- 두 조인문은 from 절 테이블 순서가 다름 
-- 행의 수가 적은 테이블을 먼저 로드하는 것이 속도 빠름 
-- 오라클 옵티마이저가 쿼리를 변경하여 둘 다 dept 테이블에 먼저 접근

select *
from dept inner join emp 
on dept.deptno = emp.deptno;


select *
from emp inner join dept
on emp.deptno = dept.deptno;



/* 서브쿼리 */
-- 1) 스칼라 : SELECT문의 SELECT절(컬럼값자리)에 사용된 SELECT문 --> 단일값(스칼라)

SELECT * FROM emp;
SELECT ROUND(AVG(sal)) FROM emp;


-- 위 쿼리의 결과물을 같이 조회 - CROSS JOIN

SELECT * 
FROM emp CROSS JOIN (SELECT ROUND(AVG(sal)) FROM emp) t; 


-- 스칼라 서브쿼리를 이용

SELECT emp.*,(SELECT ROUND(AVG(sal)) FROM emp) 평균 FROM emp;

SELECT emp.*,(SELECT deptno FROM dept WHERE deptno = 10)  FROM emp;

-- error
SELECT emp.*, (SELECT deptno FROM dept) FROM emp; -- 스칼라 결과값은 항상 단일값.



-- 2) 인라인뷰 : SELECT문의 FORM절
-- emp 테이블과 customer 테이블에 동일한 아이디를 사용할 수 없는 경우 중복 아이디 검사

SELECT *
FROM (SELECT * FROM emp WHERE gender = 'F') t
WHERE t.deptno = 20;

SELECT *
FROM emp
WHERE gender = 'F' and deptno=20;

-- 쇼핑몰 emp 테이블, customer 테이블 -> 사용자ID는 두테이블에서 중복되어서는 안 됨
-- 'test'라는 아이디가 사용가능한지 검사

SELECT * FROM emp WHERE emp_id = 'test'; -- null
SELECT * FROM customer WHERE customer_id = 'test'; -- null
-- 두 쿼리의 결과가 모두 null일때 'test'아이디를 사용가능

-- 두개의 검사쿼리를 하나의 쿼리로 조회
SELECT ec.id
FROM 
    (SELECT emp_id id FROM emp
    UNION ALL -- 집합연산자
    SELECT customer_id id FROM customer) ec -- 인라인뷰
WHERE ec.id = 'test';


-- 3) 서브쿼리 : WHERE, HAVING, ... , IN, ANY, ALL

-- a) 단일행 단일컬럼 비교(1행 1컬럼값 비교)
-- 전체 평균보다 높은 사원

SELECT emp.*
FROM emp
WHERE sal > (SELECT AVG(sal) FROM emp);

-- 전체 평균보다 부서평균이 낮은 부서

SELECT deptno, AVG(sal)
FROM emp
GROUP BY deptno
HAVING AVG(sal) < (SELECT AVG(sal) FROM emp);


-- b) 다중컬럼값 비교(1행 다중컬럼값 비교)
SELECT emp.*
FROM emp
WHERE (deptno, gender) = (SELECT deptno, gender FROM emp WHERE ename = 'SCOTT');


-- c) 다중행 단일컬럼 비교(다중행 1컬럼값 비교)

SELECT emp.*
FROM emp
WHERE sal IN (SELECT sal FROM emp WHERE deptno = 10);

SELECT emp.*
FROM emp
WHERE sal >ANY (SELECT sal FROM emp WHERE deptno = 10);

SELECT emp.*
FROM emp
WHERE sal >ALL (SELECT sal FROM emp WHERE deptno = 10);


-- d) 다중행 다중컬럼값 비교 - IN
SELECT emp.*
FROM emp
WHERE (deptno, gender) 
IN(SELECT deptno, gender FROM emp WHERE ename = 'SCOTT' OR ename = 'JAMES');



/* GROUP BY 확장 (표준 X) */
-- 1)
SELECT gender, COUNT(*)
FROM emp
GROUP BY gender;
-- 2)
SELECT deptno, COUNT(*)
FROM emp
GROUP BY deptno;

-- 3)
SELECT deptno, gender, COUNT(*)
FROM emp
GROUP BY deptno, gender
ORDER BY deptno ASC, gender DESC;

-- 4)
SELECT deptno, gender, COUNT(*)
FROM emp
GROUP BY gender, deptno
ORDER BY deptno ASC, gender DESC;

-- 집합연산을 사용
SELECT gender, null, COUNT(*) FROM emp GROUP BY gender
UNION ALL
SELECT null, NVL(deptno, 0), COUNT(*) FROM emp GROUP BY deptno;

-- GROUPING SETS()를 사용
SELECT gender, deptno, COUNT(*)
FROM emp
GROUP BY GROUPING SETS(deptno, gender)
ORDER BY gender;

-- 집합연산을 사용
SELECT gender, null, COUNT(*) FROM emp GROUP BY gender
UNION ALL
SELECT null, deptno, COUNT(*) FROM emp GROUP BY deptno
UNION ALL
SELECT null, null, COUNT(*) FROM emp GROUP BY ();

-- GROUPING SETS()를 사용
SELECT gender, deptno, COUNT(*)
FROM emp
GROUP BY GROUPING SETS((),gender, deptno)
ORDER BY gender;

-- GROUPING() 함수
SELECT null , COUNT(*)
FROM emp
GROUP BY ()
UNION ALL
SELECT deptno, COUNT(*)
FROM emp
GROUP BY deptno;

-- GROUP BY확장절 사용시 집계의 null값과 전체의 null값을 구분가능한 GROUPING 함수를 사용할 수 있음 
SELECT deptno, count(*), GROUPING(deptno)
FROM emp
GROUP BY GROUPING SETS((), deptno);


SELECT CASE WHEN deptno IS NULL AND GROUPING(deptno)  = 0 THEN '부서없음'
            WHEN deptno IS NULL AND GROUPING(deptno)  = 1 THEN '전체'
            ELSE TO_CHAR(deptno) END dept
    , count(*), GROUPING(deptno)
FROM emp
GROUP BY GROUPING SETS((), deptno);


















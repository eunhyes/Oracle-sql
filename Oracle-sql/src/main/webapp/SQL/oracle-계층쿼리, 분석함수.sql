
/* GROUP BY 확장 */

-- 1. ROLL UP()

-- 1) group by gender + 2) group by ()
select gender, count(*)
from emp
group by rollup(gender); 

-- 1) group by gender, deptno+ 2) group by gender + 3) group by ()
select gender, deptno, count(*)
from emp
group by rollup(gender, deptno); 

-- 1) group by gender, deptno, job + 2) group by gender, deptno + 3) group by gender + 4) group by ()
select gender, deptno, job, count(*)
from emp
group by rollup(gender, deptno, job); 


-- 2. CUBE()
-- 매개값이 하나일때 cube vs rollup 그리고 grouping sets -> 결과물은 동일


/* 계층쿼리 */
-- 하나의 테이블이 셀프조인하는 구조에서 많이 발생하는 계층 구조
-- 계층 구조를 시각화시키는 쿼리를 계층쿼리라고 함.
-- start with, connect by 키워드를 사용

-- where 절은 실행순서가 계층 연산보다 빠름
-- start with == 1행조건
-- connect by == 2행부터 순방향/역방향 조건 
-- from >> where >> start with / connect by

select * -- 4)
from emp -- 1)
where deptno = 10 -- 3)
connect by prior empno = mgr start with mgr is null; -- 2)
-- start with mgr is null connect by prior empno = mgr 순서는 상관없음

-- 정방향
SELECT *
FROM emp
START WITH mgr IS NULL -- 시작 조건 
CONNECT BY PRIOR empno = mgr; -- 출력하고자하는 행의 조건(이전행을 기준으로)

-- 역방향
SELECT *
FROM emp
START WITH ename = 'SMITH'
CONNECT BY PRIOR mgr = empno;

-- where절이 있다면 계층조건 다음으로 실행 (+ join 순서는 from절 다음)
SELECT * -- 4) 
FROM emp -- 1)
WHERE ename != 'BLAKE'
START WITH mgr IS NULL -- 3)
CONNECT BY PRIOR empno = mgr; -- 2) join 순서와 같음(SELF JOIN)

-- 계층쿼리에서 사용가능한 함수와 의사컬럼 
-- level 계층에서의 자신 위치 나타내기 
SELECT rowid, ename, level FROM emp; -- error


-- LPAD(' ', level) --> 자신의 level만큼 공백 (시각화)
-- CONNECT_BY_ROOT(ename) --> 가장 상위계층(root) 표시
SELECT LPAD(' ', 3*(level-1 )) || empno, ename, mgr, level, CONNECT_BY_ROOT(ename)
FROM emp
START WITH mgr IS NULL
CONNECT BY PRIOR empno = mgr;


/* 분석함수 */
-- sal의 sum 출력하기
SELECT ename, sal, sum(sal) 
FROM emp 
GROUP BY (); -- error : 개별행을 조회 할 결과셋이 존재X

SELECT SUM(sal) FROM emp;

-- 서브쿼리 사용 
SELECT ename, sal, (SELECT SUM(sal) FROM emp) 합계
FROM emp; 

-- 분석함수 사용
SELECT ename, sal, gender, 
        SUM(sal) OVER(PARTITION BY gender), -- OVER은 1)을 복제한 계산셋을 만들어 전체를 하나의 집합으로 사용 
        AVG(sal) OVER(),
        MIN(sal) OVER(),
        MAX(sal) OVER(),
        COUNT(*) OVER(),
        COUNT(*) OVER(PARTITION BY gender) --  1)을 복제 후 두개의(gender) 집합 계산셋
FROM emp; -- 1)

-- 순위 분석함수(순서주의)
-- rank = 정렬해서 rownum 붙이기
SELECT ename, sal,
    RANK() OVER(ORDER BY sal DESC) rn, -- 1)을 복제 후 정렬 후 계산 / 동등한 순위는 같게, 다음 순위는 다음 번호를 빼고 출력 
    ROW_NUMBER() OVER(ORDER BY sal DESC) rn1, -- 동등한 순위 인식X
    DENSE_RANK() OVER(ORDER BY sal DESC) rn2, -- 동등한 순위는 같게, 다음 순위는 다음 번호로 출력 
    RANK() OVER(PARTITION BY gender ORDER BY sal DESC) rn3남여 -- 1)을 두개의 (gender)집합으로 나누고 각각 정렬 후 계산 
FROM emp -- 1)
ORDER BY rn;

-- ntile(3) = 3등급으로 나누기 
SELECT ename, sal, ntile(3) OVER(ORDER BY sal DESC)
FROM emp;



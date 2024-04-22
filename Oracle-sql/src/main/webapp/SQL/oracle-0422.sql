-- 테이블 스키마 확인 
DESC emp;
DESCRIBE emp;

-- *(애스터리스크)
SELECT * FROM emp;

-- 원하는 열 조회(열 순서는 상관X)
SELECT deptno, empno, ename, hiredate
FROM emp;

-- alias(별칭) 사용(컬럼 AS 키워드는 생략 가능)
SELECT empno AS 번호, ename 이름 
FROM emp;

-- DISTINCT 연산 

/* SELECT절 컬럼 결과값에 중복값을 제거 */
SELECT DISTINCT deptno 
FROM emp;

/* deptno + empno 값의 중복을 제거 */
SELECT DISTINCT deptno, empno 
FROM emp;

-- DISTINCT연산과 GROUP BY절의 결과셋(계산셋) 차이
-- DISTINCT == 데이터 중복 제거, 정렬 X
-- GROUP BY == 데이터 그룹화 -> 집계, 정렬 O

SELECT deptno /* 3) */
FROM emp /* 1) */
GROUP by deptno; /* 2)  GROUP BY 집계함수를 사용하기 위함 */
-- 1) 전체 결과셋 메모리에 만든다

-- 2) 1)에서 아래 4개의 계산셋을 만든다

SELECT * FROM emp where deptno = 10;

SELECT * FROM emp where deptno = 20;

SELECT * FROM emp where deptno = 30;

SELECT * FROM emp where deptno is null;

-- 3) 2)에서 deptno만 추출한다

-- 산술연산자, 연결연산자 사용
/* SELECT 절에서 사칙연산이 가능 */
SELECT ename, sal, sal*12 연봉 
FROM emp;

/* || 문자열 연결 연산자 */
SELECT ename || ',' || job   
FROM emp; 

/* Literal 조회 */
SELECT deptno, dname, loc, 'USA' 나라, 100 인구 
FROM dept;


/* DUAL 테이블 */
select 'KOREA'
FROM dual;

select sysdate
FROM dual;

/* DUAL 테이블과 연산자를 이용한 SELECT 계산기 */
SELECT 2+3 FROM dual;
SELECT 2-3 FROM dual;
SELECT 2*3 FROM dual;
SELECT 2/3 FROM dual;

SELECT '구디'||'아카데미' 
FROM dual;

-- 컬럼이 생성되는 시점은 FROM절이 아니고 FROM 다음 절 결과셋 생성시 생성
SELECT deptno, dname, loc, ROWNUM
FROM dept -- 1)
WHERE ROWNUM < 3; -- 2)
/* where 절이 생성될때 rownum이 1부터 생성되므로(행이 조건에 만족하면 다음 2, 3, 4....증가) 조건에 맞는 행이 하나도 없다  */


-- 함수
-- 1. 단일행 함수 : 결과셋 컬럼값(한행) 하나에 대한 연산
--  (문자함수, 숫자함수, 날짜함수, null함수)
-- 2. 집계함수 : 결과셋이 그룹핑되어(계산셋 == 결과셋)있을 때 그 집합에 동일한 컬럼값들에 대한 연산
-- 3. 분석함수 : 결과셋과 별도로 계산셋을 만들어 계산. 출력시 원본 결과셋과 계산셋을 합쳐서 조회 가능

-- 숫자 단일행 함수
-- ROUND(숫자, 반올림자릿수) 반올림 함수
SELECT ename, sal 연봉, ROUND(sal/12, 1) 급여
FROM emp;

-- 행번호(ROWNUM) 출력
SELECT ename, deptno, ROWNUM FROM emp
WHERE deptno = 20;

SELECT ename, deptno, ROWNUM FROM emp
WHERE deptno > 20;

-- 행의 총 개수 출력(COUNT)
SELECT deptno deptNo, COUNT(*) cnt
FROM emp
WHERE deptno IS NOT NULL
GROUP BY deptno
ORDER BY deptno ASC;

-- 날짜 단일행 함수 
SELECT EXTRACT(YEAR FROM hiredate)
FROM emp;

-- 근속년도 구하기 
SELECT EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM hiredate) 근속년도
FROM emp
WHERE (EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM hiredate)) < 20  -- where절에서는 별칭 사용X(순서때문에)
ORDER BY 근속년도 DESC; -- order절에서는 별칭 사용O

-- null 값 함수
SELECT ename, sal, comm, sal + comm 연봉
FROM emp;

SELECT ename, sal, comm, NVL(comm,0) 연봉 
-- NVL(비교값, 치환값) 비교값이 null이면 치환값으로 출력 혹은 원래값으로
-- comm이 null이면 연봉은 0으로 출력
FROM emp;

SELECT ename,
        job,
        CASE job
        WHEN 'PRESIDENT' Then '빨강'
        WHEN 'MANAGER' THEN '주황'
        WHEN 'ANALYST' THEN '노랑'
        WHEN 'CLERK' THEN '초록'
        ELSE '파랑' END color
FROM emp;

SELECT ename, job, CASE 
WHEN job = 'PRESIDENT' Then '빨강' 
WHEN job = 'MANAGER' THEN '주황'
WHEN job = 'ANALYST' THEN '노랑' 
WHEN job = 'CLERK' THEN '초록' 
ELSE '파랑' END color 
FROM emp 
ORDER BY (CASE 
WHEN color = '빨강' THEN 1
WHEN color = '주황' THEN 2 
WHEN color = '노랑' THEN 3 
WHEN color = '초록' THEN 4
ELSE 5 END) ASC;

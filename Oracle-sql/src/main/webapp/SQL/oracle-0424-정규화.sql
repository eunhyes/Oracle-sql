-- 정규화는 선행관계가 없음

CREATE TABLE 학생 (
    이름 VARCHAR2(50),
    보호자 VARCHAR2(50),
    보호자나이 NUMBER,
    나이 NUMBER,
    성별 VARCHAR2(50),
    등급 NUMBER,
    등급색상 VARCHAR2(50),
    취미 VARCHAR2(100),
    성적1 NUMBER,
    성적2 NUMBER,
    CONSTRAINT 학생기본키 PRIMARY KEY(이름, 보호자) 
);

DROP TABLE 학생;

ALTER TABLE 학생 ADD 보호자나이 NUMBER;

INSERT INTO 학생 VALUES('김일', '김구', 50, 20, '남', 2, '주황', '야구, 축구', 80, 80);
INSERT INTO 학생 VALUES('김이', '김구', 53, 18, '여', 1, '빨강', '발야구, 클라이밍', 95, 100);
INSERT INTO 학생 VALUES('김삼', '김유신', 49, 20, '남', 5, '파랑', '야구', 50, 50);
INSERT INTO 학생 VALUES('이일', '이율곡', 60, 20, '남', 1, '빨강', '야구, 축구, 수영', 100, 100);
INSERT INTO 학생 VALUES('이이', '이순신', 50, 20, '여', 2, '주황', '축구, 클라이밍', 90, 90);
INSERT INTO 학생 VALUES('이이', '이율곡', 60, 20, '남', 3, '노랑', '야구, 수영, 클라이밍', 70, 70);

SELECT * FROM 학생;

commit;

-- 선행조건 : 테이블에 기본키는 존재해야 함.
-- 제 1정규화   
-- 1) 컬럼값이 멀티값이 존재해서는 안됨. --> 정규화에 위배된다 = 속성값이 다른 table로 나가야 됨.
SELECT * FROM 학생
WHERE 취미 = '야구'; 
-- 야구만 나옴

SELECT * FROM 학생
WHERE 취미 LIKE '%야구%';
-- 발야구까지 나옴

-- 취미 테이블을 만들면서 학생기본키(이름, 보호자) + 취미 를 외래키와 기본키로 설정
-- 학생테이블에서 취미컬럼 삭제 -> 외래키로 연결된 취미테이블 생성 = 제 1정규화
SELECT * FROM 학생;
SELECT * FROM 취미;

SELECT * FROM 취미
WHERE 취미 = '야구';

SELECT * FROM 취미
INNER JOIN 학생
ON 학생.이름 = 취미.이름
AND 학생.보호자 = 취미.보호자터
WHERE 취미.취미 = '야구';
-- 학생테이블의 이름과 취미테이블의 이름이 같고, 학생테이블의 보호자와 취미테이블의 보호자가 같은 것 중에 
-- 취미테이블의 취미가 야구인 데이터


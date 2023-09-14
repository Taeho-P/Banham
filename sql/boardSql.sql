----**db최초상태!!>>> 맨 처음 properties create로 생성시 모두 복사하여 전부 실행**

----**그 후, create!!>>> (시퀀스, 함수는 제외) 1.comment(댓글테이블)이 있는지확인 없으면만들어주세요, 2.file테이블 재생성.
----**(파일첨부 저장시 generated key오류 발생)tb_adopt_file, tb_missing_file은 db에서 지운 후, 다시 쿼리 실행하여 재생성**
----** 시퀀스 목록 확인
----** seq_adopt_c, seq_adopt_file, seq_missing_c, seq_missing_file

----입양댓글테이블----check
create table tb_adopt_comment
(id number(10) NOT null PRIMARY KEY,
 board_id number(10,0) NOT NULL,
 CONSTRAINT fk_adoptcomment_id FOREIGN key(board_id) REFERENCES tb_adopt_board (id),
 content varchar2(1000) NOT null,
 writer varchar2(100) NOT NULL,
 delete_yn number(1,0) CHECK (delete_yn IN (1, 0)),
 created_date DATE  DEFAULT sysdate NOT NULL,
 modified_date DATE,
 member_id number(10,0));

----입양파일----생성되어있는걸 지우고 실행 check
CREATE TABLE tb_adopt_file (
id number(10,0) NOT NULL PRIMARY KEY,
board_id number(10,0) NOT NULL,
original_name varchar2(255) NOT NULL,
 store_name varchar2(60) NOT NULL
);

--입양파일외래키--check
ALTER TABLE tb_adopt_file ADD CONSTRAINT fk_adopt_id FOREIGN key(board_id) REFERENCES tb_adopt_board (id);


--입양댓글시퀀스--제외
CREATE SEQUENCE seq_adopt_c START WITH 1 INCREMENT BY 1 NOCYCLE NOCACHE;

--입양파일시퀀스--제외
CREATE SEQUENCE seq_adopt_file START WITH 1 INCREMENT BY 1;


--입양파일 함수--제외
CREATE FUNCTION get_adopt RETURN NUMBER IS
BEGIN
    RETURN seq_adopt_file.nextval;
END;



-----------------------------------------------------실종 게시판 ver 귀찮음을 드려 죄송합니다
----실종 댓글----check
create table tb_missing_comment
(id number(10) NOT null PRIMARY KEY,
 board_id number(10,0) NOT NULL,
 CONSTRAINT fk_miscomment_id FOREIGN key(board_id) REFERENCES tb_missing_board (id),
 content varchar2(1000) NOT null,
 writer varchar2(100) NOT NULL,
 delete_yn number(1,0) CHECK (delete_yn IN (1, 0)),
 created_date DATE  DEFAULT sysdate NOT NULL,
 modified_date DATE,
 member_id number(10,0));

----실종 파일----check
CREATE TABLE tb_missing_file (
id number(10,0) NOT NULL PRIMARY KEY,
board_id number(10,0) NOT NULL,
original_name varchar2(255) NOT NULL,
store_name varchar2(60) NOT NULL);

--실종 파일외래키--
ALTER TABLE tb_missing_file ADD CONSTRAINT fk_missing_id FOREIGN key(board_id) REFERENCES tb_missing_board (id);



--실종 댓글 시퀀스--제외
CREATE SEQUENCE seq_missing_c START WITH 1 INCREMENT BY 1 NOCYCLE NOCACHE;

--실종 파일 시퀀스--제외
CREATE SEQUENCE seq_missing_file START WITH 1 INCREMENT BY 1;
--실종 파일 함수--제외
CREATE FUNCTION get_missing RETURN NUMBER IS
BEGIN
    RETURN seq_missing_file.nextval;
END;

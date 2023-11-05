---------------------------------------------------------CREDIT TABLE------------------------------------------------------------

CREATE TABLE CREDIT(
 CREDIT_ID NUMBER NOT NULL PRIMARY KEY,
 USER_ID NUMBER NOT NULL REFERENCES MEMBER(USER_ID),
 GRADUATE_CREDIT NUMBER NOT NULL
);

create sequence credit_seq; 
drop table credit cascade constraints purge;
drop sequence credit_seq; 

INSERT INTO CREDIT(
 CREDIT_ID, USER_ID, GRADUATE_CREDIT 
)
VALUES(
 credit_seq.NEXTVAL, 0, 150
);

---------------------------------------------------------SEMESTER TABLE------------------------------------------------------------

drop table semester cascade constraints purge; 
drop sequence credit_semester_seq;


CREATE TABLE SEMESTER(
 SEMESTER_ID NUMBER NOT NULL PRIMARY KEY,
 CREDIT_ID NUMBER NOT NULL REFERENCES CREDIT(CREDIT_ID),
 SEMESTER_NAME VARCHAR2(100) NOT NULL
);


create sequence semester_seq; -- semester 시퀀스 생성 
drop sequence semester_seq;

INSERT INTO SEMESTER(
 SEMESTER_ID, CREDIT_ID, SEMESTER_NAME 
)
VALUES(
 semester_seq.NEXTVAL, 1, '0101'
);

---------------------------------------------------------SEMESTER_DETAIL TABLE------------------------------------------------------------

drop table semester_detail cascade constraints purge; 
drop sequence semester_detail_seq;

create sequence semester_detail_seq;

CREATE TABLE SEMESTER_DETAIL(
 SEMESTER_DETAIL_ID NUMBER NOT NULL PRIMARY KEY,
 SEMESTER_ID NUMBER NOT NULL REFERENCES SEMESTER(SEMESTER_ID),
 SUBJECT VARCHAR2(100),
 CREDIT NUMBER NOT NULL,
 GRADE VARCHAR2(100) NOT NULL,
 MAJOR VARCHAR2(100) NOT NULL
);

drop table comments3 cascade constraints purge;

CREATE TABLE comments3(
	num			number	primary key,
	id			varchar2(30) references member3(id),
	content		varchar2(200),
	reg_date	date,
	board_num	number	references board3(board_num)	
						on delete cascade
);

--게시판 글이 삭제되면 참조하는 댓글도 삭제됩니다.--
--member3가 삭제되면 member3의 댓글도 삭제됩니다.--

drop sequence com_seq3;

--시퀀스를 생성합니다.
create sequence com_seq3;

select * from comments3;


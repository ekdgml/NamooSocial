-- DB 생성 및 사용자 계정 추가
CREATE DATABASE namoosocialdb2;
GRANT ALL PRIVILEGES ON namoosocialdb2 TO namoouser.* IDENTIFIED BY 'namoouser' WITH GRANT OPTION;
FLUSH PRIVILEGES;
-- 사용자
DROP TABLE IF EXISTS user_tb RESTRICT;

-- 메시지
DROP TABLE IF EXISTS message_tb RESTRICT;

-- 사용자관계
DROP TABLE IF EXISTS usertouser_tb RESTRICT;

-- 사용자
CREATE TABLE user_tb (
	user_id  VARCHAR(20) PRIMARY KEY , -- 사용자아이디
	user_nm  VARCHAR(20) NULL,     -- 이름
	email    VARCHAR(50) NULL,     -- 이메일
	password VARCHAR(20) NULL      -- 비밀번호
);

-- 메시지
CREATE TABLE message_tb (
	msg_id    INTEGER       PRIMARY KEY AUTO_INCREMENT, -- 메시지아이디
	contents  VARCHAR(2000) NULL,     -- 내용
	writer_id VARCHAR(20)   NULL,     -- 작성자
	reg_dt    TIMESTAMP     NULL      -- 작성일시
);

-- 사용자관계
CREATE TABLE usertouser_tb (
	from_id VARCHAR(20) NOT NULL, -- 누가
	to_id   VARCHAR(20) NOT NULL  -- 누구를
);

-- 사용자관계
ALTER TABLE usertouser_tb
	ADD CONSTRAINT PK_UserToUser_TB -- 사용자관계 Primary key
		PRIMARY KEY (
			from_id, -- 누가
			to_id    -- 누구를
		);

-- 메시지
ALTER TABLE message_tb
	ADD CONSTRAINT FK_USER_TB_TO_MESSAGE_TB -- 사용자 -> 메시지
		FOREIGN KEY (
			writer_id -- 작성자
		)
		REFERENCES user_tb ( -- 사용자
			user_id -- 사용자아이디
		);
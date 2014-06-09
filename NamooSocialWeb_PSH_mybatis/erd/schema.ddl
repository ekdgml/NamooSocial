DROP TABLE IF EXISTS user_tb RESTRICT;

DROP TABLE IF EXISTS message_tb RESTRICT;

DROP TABLE IF EXISTS usertouser_tb RESTRICT;

create table user_tb (
	user_id  varchar(20) not null, 
	name     varchar(20) null,    
	email    varchar(20) null,     
	password varchar(20) null,
	img_type varchar(50) null,     
	img_file varchar(30) null     
);

alter table user_tb
	add constraint pk_user_tb 
		primary key (
			user_id 
		);

create table message_tb (
	msg_id   integer       not null, 
	contents varchar(2000) null,     
	writer_id   varchar(20)   null,
	reg_dt   timestamp     null
);

alter table message_tb
	add constraint pk_message_tb 
		primary key (
			msg_id 
		);

alter table message_tb
	modify column msg_id integer not null auto_increment;

alter table message_tb
	auto_increment = 1;

create table usertouser_tb (
	who  varchar(20) not null, 
	whom varchar(20) not null  
);

alter table usertouser_tb
	add constraint pk_usertouser_tb 
		primary key (
			who,  
			whom  
		);

alter table message_tb
	add constraint fk_user_tb_to_message_tb 
		foreign key (
			writer_id
		)
		references user_tb ( 
			user_id 
		);
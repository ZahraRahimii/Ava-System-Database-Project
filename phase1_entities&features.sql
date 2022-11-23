CREATE TABLE `user` (
    name VARCHAR(20),
    famliy_name VARCHAR(20),
    user_name VARCHAR(20) not null primary key,
    pass VARCHAR(120),
    birthday DATE,
    membership_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    bio VARCHAR(64)
);

CREATE TABLE `hashtag2` ( 
	hashtId int not null PRIMARY KEY AUTO_INCREMENT PRIMARY KEY,
	content varchar(6)
);

CREATE TABLE `ava5` (
	ava_id int not null AUTO_INCREMENT PRIMARY KEY,
    commentToAva int,
	user_name varchar(20) not null,
    content varchar(256),
    send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN key (user_name) REFERENCES `user`(user_name),
    FOREIGN key (commentToAva) REFERENCES `ava5`(ava_id)
);

CREATE TABLE `blocking2`(
	user1 varchar(20),
	user2 varchar(20),
	PRIMARY KEY (user1, user2),
    foreign key (user1) references `user`(user_name),
    foreign key (user2) references `user`(user_name)
);

create table `following` (
	user1 varchar(20),
	user2 varchar(20),
	PRIMARY KEY (user1, user2),
	foreign key (user1) references `user`(user_name),
    foreign key (user2) references `user`(user_name)
);

CREATE TABLE `login2` (
	user_name varchar(20) ,
    passwd varchar(120),
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (user_name) references `user`(user_name),
    primary key (user_name, login_time)
);

CREATE TABLE `message7` (
    message_id int AUTO_INCREMENT PRIMARY KEY,
    content varchar(256),
    ava_id int,
    sender_id varchar(20) not null ,
    delivered_id varchar(20) not null ,
    send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN key (sender_id) REFERENCES `user`(user_name),
    FOREIGN key (delivered_id) REFERENCES `user`(user_name),
    FOREIGN key (ava_id) REFERENCES `ava5`(ava_id),
     check ((content != null or ava_id != null) and (content = null or ava_id = null))
);

create table `ava_hashtag8`(
    avaId int not null,
    hashtagId int not null,
    foreign key (avaId)  references `ava5`(ava_id),
	foreign key (hashtagId) REFERENCES `hashtag2`(hashtId)
);

create table `ava_like3` (
	ava__id int,
    user__name varchar(20),
    liked varchar(1),
	foreign key (ava__id)  references `ava5`(ava_id),
	foreign key (user__name)  references `user`(user_name),
    primary key(ava__id, user__name)
);

insert into `user` (`name`, `famliy_name`, `user_name`, `pass`, `birthday`)
values ('b', 'v', 'abcdf', md5('sdf'), '2020-12-23');

select * from `user`;

insert into `hashtag2` (`content`) 
select ('#avaaa') 
	where length('#avaaa') = 6 
	and '#avaaa' LIKE '#%' 
	and not exists (select * from `hashtag2` where `content` = '#avaaa');
select * from `hashtag2`;

-- --- لاگین کردن
insert into `login2`(`user_name`, `passwd`)
select 'abcdf', md5('sdf')
where exists (select * from `user` where `user_name` = 'abcdf' and `pass` = md5('sdf'));  

-- --- دریافت کسانی که لاگین کرده اند و مشخصات آنها
with `log1`(`login_status`) as (select 'login succeed')
select `user`.`user_name`, `login_time`, `login_status`  
from `user`,`login2`, `log1`
where `user`.user_name = `login2`.user_name 
order by `login_time` desc;

-- -------- آوا گذاشتن به همراه هشتگ
insert into `ava5`(`user_name`, `content`)
select 'abcdf', 'I am going to my office guys...'
where  exists ( select * from `login2` where `login2`.`user_name` = 'abcdf');

insert into `ava_hashtag8` (`avaId`, `hashtagId`)
values (14, 5);

select * from `ava5`;

-- view of ava hashtag (دیدن آوا ها به همرا هشتگ)
create view ava__hashtag10 as
-- SELECT a.*, ah.*, h.hashtId ,cast(concat('[', group_concat(json_quote(h.`content`) SEPARATOR ','), ']') as json) AS hashtag_package
SELECT a.*, ah.*, h.hashtId ,group_concat((h.`content`)) as `hashtag_package` 
FROM `ava5` as a
	left outer JOIN `ava_hashtag8` as ah
		ON a.`ava_id` = ah.`avaId`
	left JOIN `hashtag2`as h
		 ON ah.`hashtagId` = h.`hashtId`
	group by a.`ava_id`;

-- دیدن همه آواها
select `ava_id`, `user_name`, `content`, `send_date`, `hashtag_package` 
from ava__hashtag10
where commentToAva is null and `user_name` = 'abcdf';
-- ------------
insert into `following`
select 'abc', 'abcdf'
	where 'abc' != 'abcdf';

select * from `blocking2`;
select * from `following`;

delete from `following` where `user1` = 'bv' and `user2` = 'fr';

insert into `blocking2`
select 'abcdf', 'ab'
where 'abcdf' != 'ab';

delete from `blocking2` where `user1` = 'fr' and `user2` = 'abc';

-- ----------- daryaft fa@liat followers haye fr (bv, zr)
select `user1` as me, `user_name` as my_following, `content` as ava, `send_date`, `hashtag_package` 
FROM 
	`following` 
    Inner Join 
	ava__hashtag10
    on `user1` = 'abc' and `user2` = `user_name` and (`user2`, `user1`) not in 
		(select * from `blocking2`)
	order by `send_date` desc;
    
-- ------- see ava haye baghie
select `user_name` , `content` as ava, `send_date`, `hashtag_package`  from ava__hashtag10
	where `user_name` = 'abcdf' 
		and ('abcdf', 'abc') not in 
		(select * from `blocking2`);

-- inserting to comments by the condtion of not being blocked : a comments bv's ava (which bv blocks a)
insert into `ava5`(`user_name`, `content`, `commentToAva`)
with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = 8)
select 'ab', 'how are you zahra?', 8
	 where not exists ((select `u` 
		from `id2username8` 
			where (`id2username8`.`u`, 'ab') in (select * from `blocking2`) 
                    ))
		and not exists (select `u` 
				from `id2username8` 
					where (`id2username8`.`u`, 'ab') in (select * from `blocking2`)
                    )
		and exists (select * from `login2` where `user_name` = 'ab');

select * from `blocking2`;
select * from `ava5` where `commentToAva`is not null;

insert into `ava_hashtag8` (`avaId`, `hashtagId`)
values (9, 3);

-- a mikad comment haye ava_id = 1 (zr) ro bebine

with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = 7)

(select ah1.`ava_id`, ah1.`user_name`, ah1.`content`, ah1.`send_date`, ah1.`hashtag_package`, 
ah2.`commentToAva`, ah2.`ava_id`, ah2.`user_name`, ah2.`content`, ah2.`send_date`, ah2.`hashtag_package` from (
		ava__hashtag10 as ah1
		inner join ava__hashtag10 as ah2
        on ah1.`ava_id` = 7 and ah1.`ava_id` = ah2.`commentToAva`
        and not exists 
			(select `u` from `id2username` 
				where (`id2username`.`u`, 'zr') in (select * from `blocking2`))
	)
)
union
   select a.`ava_id`, a.`user_name`, a.`content`, a.`send_date`, a.`hashtag_package`,
   '--','--', '--', '--', '--','--' 
    from 
		ava__hashtag10 as a
        where a.`ava_id` = 7
        and not exists 
			(select `u` from `id2username` 
				where (`id2username`.`u`, 'zr') in (select * from `blocking2`))
    ;


-- see hashtags with #abced
select ah.`ava_id`, ah.`user_name`, ah.`send_date`, ah.`hashtag_package` from ava__hashtag8 as ah 
	where ah.`hashtag_package` like '%#abcde%';
    
-- -------- like ava
insert into `ava_like3`
with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = 12)
select 12, 'abc', '1'
where not exists ( 
	select `u` from `id2username` 
		where (`id2username`.`u`, 'abc') in (select * from `blocking2`)
	);
    
select * from `ava_like3`;
select * from `ava5`;
select * from `blocking2`;

-- --- like number 'a' blocke :) ! hnam momkene taraf ava nadashte bashe aslan
with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = 12)
select count(`liked`) from `ava_like3` 
where `ava__id` = 12 and not exists ( 
	select `u` from `id2username` 
		where (`id2username`.`u`, 'abc') in (select * from `blocking2`)
	);
    
-- -- دیدن آوا به همراه لایک ها
with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = 7)
select * 
	from `ava5` as a,`ava_like3` as l
    where a.`ava_id` = 7 and a.`ava_id` = l.`ava__id` and (`user__name`, 'ac') not in  (select * from `blocking2`)
  	and (`user_name`, 'ac') not in (select * from `blocking2`);
-- ------------------------

-- --- popular ava
select *, count(`liked`)
	from `ava5` as a,`ava_like3` as l
    where a.`ava_id` = l.`ava__id` and (`user_name`, 'ac') not in (select * from `blocking2`)
    group by (`user_name`)
    order by count(`liked`) desc;


-- --- message:

insert into `message7` (`content`, `ava_id`, `sender_id`, `delivered_id`)
with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = 3)
select  'Are you ok? for sure?', null, 'bv', 'abcd'
where (('abcd', 'bv') not in (select * from `blocking2`)) and (null is null or not exists ( 
	select `u` from `id2username` 
		where (`id2username`.`u`, 'bv') in (select * from `blocking2`)
	));
-- -----------------------
insert into `message7` (`content`, `ava_id`, `sender_id`, `delivered_id`)
with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = 1)
select  null, 1, 'ab', 'abcd'
where (('abcd', 'ab') not in (select * from `blocking2`)) and (1 is null or not exists ( 
	select `u` from `id2username` 
		where (`id2username`.`u`, 'ab') in (select * from `blocking2`)
	));

select * from `blocking2`;
select * from `ava5`;
select * from `user`;
select * 
	from `message7` as m;

-- -- getting messages

with abc_messages1 as (
(select `sender_id`, m.`send_date`
	from `ava5` as a
	inner join `message7` as m 
    on m.`delivered_id` = 'abcd' and (m.`ava_id` = a.`ava_id`) 
    )
union
	select `sender_id`, `send_date`
    from  `message7` as m
    where `content` is not null and m.`delivered_id` = 'abcd')
    
select `sender_id`, `send_date` from abc_messages1 order by `send_date` desc;


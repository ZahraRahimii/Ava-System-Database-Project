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
	ava_id int AUTO_INCREMENT PRIMARY KEY,
    commentToAva int,
	user_name varchar(20) not null,
    content varchar(256),
    send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN key (user_name) REFERENCES `user`(user_name),
    FOREIGN key (commentToAva) REFERENCES `ava5`(ava_id)
);

create table `ava_log1` (
	ava_id int,
    user_name varchar(20),
    send_date TIMESTAMP
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

CREATE TABLE `create_account_log2` (
	user_name varchar(20) ,
    login_time TIMESTAMP ,
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

create view ava__hashtag10 as
SELECT a.*, ah.*, h.hashtId , group_concat((h.`content`)) as `hashtag_package` 
FROM `ava5` as a
	left outer JOIN `ava_hashtag8` as ah
		ON a.`ava_id` = ah.`avaId`
	left JOIN `hashtag2`as h
		 ON ah.`hashtagId` = h.`hashtId`
	group by a.`ava_id`;

DELIMITER //
-- -------------------------------------------
create procedure create_account2(
	in name VARCHAR(20),
	in family_name VARCHAR(20),
    in user_name VARCHAR(20),
    in pass VARCHAR(120),
    in birthday DATE,
    in bio VARCHAR(64)
)
begin
	 insert into `user` (`name`, `famliy_name`, `user_name`, `pass`, `birthday`, `bio`)
	 values (name, family_name, user_name, md5(pass), birthday, bio);
    -- select * from `user`;

end //
-- ----------------------------------------
create procedure loginIntoAccount3(
	in user_name varchar(20),
    in passwd varchar(120)
)
begin
	insert into `login2`(`user_name`, `passwd`)
	select user_name, md5(passwd)
	where exists (select * from `user` where `user_name` = user_name and `pass` = md5(passwd)); 
    
end //
-- -----------------------------------------
create procedure getLastLogin3(
	out id varchar (20)
)
begin
	select `user_name` into id
    from `login2`
    order by login_time desc
    limit 1;
    
end //
-- -----------------------------------------
create procedure getLogins2()
begin
	declare user_name1 varchar(20);
    call getLastLogin3(user_name1);
    
	select `login_time`
    from `login2`
    where `user_name` = user_name1
    order by login_time desc;
    
end //
-- -----------------------------------------
create procedure login2(
	in user_name varchar(20),
    in pass varchar(120)
)
begin
    
	insert into `login2`(`user_name`, `passwd`)
	select user_name, md5(pass)
	where exists 
		(select * from `user` where `user_name` = user_name and `pass` like md5(pass));  
	select * from `login2`;
    
end //
-- -----------------------------------------
create procedure post_ava11(
    in content varchar(256)
)
begin
	declare user_name varchar(20);
    call getLastLogin3(user_name);
    
	insert into `ava5`(`user_name`, `content`)
	value (user_name, content);
    
end //
-- -----------------------------------------
create procedure make_hashtag12(
    in hashtag varchar(6),
    out hashtag_id int
)
begin
	insert into `hashtag2` (`content`) 
	select (hashtag) 
	where length(hashtag) = 6 
		and hashtag LIKE '#%' 
		and not exists (select * from `hashtag2` where `content` = hashtag);
        
	select `hashtId` into hashtag_id
    from `hashtag2`
    where `content` like hashtag
    limit 1;
	-- select * from `hashtag2`;
	-- select hashtId into row_num
    -- from `hashtag2`
    -- order by hashtId desc
    -- limit 1;

end //
-- -----------------------------------------
create procedure get_last_hasht_id2 (out h_count int)
begin
    select hashtId into h_count
    from `hashtag2`
    order by hashtId desc
    limit 1;
end//
-- -----------------------------------------
create procedure add_hashtag_to_ava(
    in ava_id int,
    in hashtag_id int
)
begin
	insert into `ava_hashtag8` (`avaId`, `hashtagId`)
	values (ava_id, hashtag_id);
end //
-- -----------------------------------------
create procedure get_my_ava13()
begin
	declare user_id varchar(20);
    call getLastLogin3(user_id);
    
    SELECT a.`user_name`, a.`content`, a.`send_date`
    -- , group_concat((h.`content`)) as `hashtag_package` 
	FROM `ava5` as a
		-- left JOIN `ava_hashtag8` as ah
			-- ON a.`ava_id` = ah.`avaId`
		-- left JOIN `hashtag2`as h
			-- ON ah.`hashtagId` = h.`hashtId`
		where a.`user_name` = user_id and a.`commentToAva` is null
        order by a.`send_date` desc;
       -- group by a.`ava_id`;
end //
-- -----------------------------------------
create procedure follow2(
	in user_name2 varchar(20)
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3(my_user_name);
	insert into `following`
	select my_user_name , user_name2
	where my_user_name != user_name2;

end //
-- -----------------------------------------
create procedure unfollow1(
	in user_name2 varchar(20)
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3(my_user_name);
    
	delete from `following`
	where `user1` = my_user_name and `user2` = user_name2;

end //
-- -----------------------------------------
create procedure block1(
	in user_name2 varchar(20)
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3(my_user_name);
	insert into `blocking2`
	select my_user_name , user_name2
	where my_user_name != user_name2;

end //
-- -----------------------------------------
create procedure unblock1(
	in user_name2 varchar(20)
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3(my_user_name);
	delete from `blocking2`
	where `user1` = my_user_name and `user2` = user_name2;

end //
-- -----------------------------------------
create procedure get_followings_activities4()
begin
	declare my_user_name varchar(20);
    call getLastLogin3(my_user_name);
    
	select `user1`, `user_name`, `content`, `send_date`
	FROM 
		`following` 
		Inner Join 
		`ava5`
		on `user1` = my_user_name and `user2` = `user_name` and (`user2`, `user1`) not in 
			(select * from `blocking2`)
		order by `send_date` desc;

end //
-- -----------------------------------------
create procedure get_sb_activities6(
	in sb_user_name varchar(20)
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3(my_user_name);
    
    select `user_name` , `content` as ava, a.`send_date`
    from `ava5` as a
	where `user_name` = sb_user_name
		and (sb_user_name, my_user_name) not in 
		(select * from `blocking2`)
	order by a.`send_date` desc;

end //
-- -----------------------------------------
create procedure comment_on_ava2(
	in content varchar(256),
    in sb_ava_id int
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3(my_user_name);
    
   insert into `ava5`(`user_name`, `content`, `commentToAva`)
	with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = sb_ava_id)
	select my_user_name, content, sb_ava_id
		 where not exists (
			(select `u` 
				from `id2username` 
					where (`id2username`.`u`, my_user_name) in 
						(select * 
							from `blocking2`) 
			)
		);
end //
-- -----------------------------------------
create procedure comment_on_ava2(
	in content varchar(256),
    in sb_ava_id int
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3(my_user_name);
    
   insert into `ava5`(`user_name`, `content`, `commentToAva`)
	with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = sb_ava_id)
	select my_user_name, content, sb_ava_id
		 where not exists (
			(select `u` 
				from `id2username` 
					where (`id2username`.`u`, my_user_name) in 
						(select * 
							from `blocking2`) 
			)
		);
end //
-- -----------------------------------------
create procedure get_comments_of_ava9(
	in sb_ava_id int
)
begin
	
    declare my_user_name varchar(20);
    call getLastLogin3(my_user_name);
    
	with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = sb_ava_id)
	(
	-- select ah1.`ava_id`, ah1.`user_name`, ah1.`content` as `ava`, ah1.`send_date` as `ava sent in`, 
	-- group_concat((ah2.`content`) SEPARATOR "  |  ") as `comments`
    select ah1.`ava_id`, ah1.`user_name`, ah1.`content`, ah1.`send_date`, ah1.`hashtag_package`, 
	ah2.`commentToAva`, ah2.`ava_id` as `comment_id`, ah2.`user_name`, ah2.`content`, ah2.`send_date`, ah2.`hashtag_package`
		from
			ava__hashtag10 as ah1
			inner join ava__hashtag10 as ah2
				on ah1.`ava_id` = sb_ava_id and ah1.`ava_id` = ah2.`commentToAva`
				where not exists 
					(select `u` from `id2username` 
						where (`id2username`.`u`, my_user_name) in (select * from `blocking2`))
	)
	union
	   select a.`ava_id`, a.`user_name`, a.`content`, a.`send_date`, a.`hashtag_package`,
	   '--','--', '--', '--', '--','--' 
		from 
			ava__hashtag10 as a
			where a.`ava_id` = sb_ava_id
			and not exists 
				(select `u` from `id2username` 
					where (`id2username`.`u`, my_user_name) in (select * from `blocking2`))
		;
end //
-- -----------------------------------------
create procedure ava_with_specific_hashtag8(
	in content varchar(6)
)
begin
	declare my_user_name varchar(20);
	declare new_content varchar(8);
    call getLastLogin3(my_user_name);
    
	select CONCAT("%", content, "%") into new_content;
	select ah.`ava_id`, ah.`content`, ah.`user_name`, ah.`send_date`, ah.`hashtag_package` 
	from ava__hashtag10 as ah 
	where ( ah.`user_name`, my_user_name) not in (select * from `blocking2`) and ah.`hashtag_package` like new_content
    order by ah.`send_date` desc;
    
end //
-- -----------------------------------------
create procedure like_ava2(
	in sb_ava_id int,
	in liked int
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3(my_user_name);
    
  insert into `ava_like3`
	with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = sb_ava_id)
	select sb_ava_id, my_user_name, liked
	where not exists ( 
		select `u` from `id2username` 
			where (`id2username`.`u`, my_user_name) in (select * from `blocking2`)
		);
end //
-- -----------------------------------------
create procedure likes_number3(
	in sb_ava_id int,
    in liked_type int
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3 (my_user_name);
    
	with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = sb_ava_id)
	select count(`liked`) from `ava_like3` 
	where `ava__id` = sb_ava_id and not exists ( 
		select `u` from `id2username` 
			where (`id2username`.`u`, my_user_name) in (select * from `blocking2`)
		) and `liked` = liked_type;
end //
-- -----------------------------------------
create procedure user_name_whom_like1(
	in sb_ava_id int,
    in liked_type int
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3 (my_user_name);
    
    with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = sb_ava_id)
	select l.`user__name`
		from `ava_like3` as l
		where l.`liked` = liked_type 
			and l.`ava__id` = sb_ava_id 
			and (l.`user__name`, my_user_name) not in (select * from `blocking2`)
            and
            not exists (select `u` from `id2username` where (`id2username`.`u`, my_user_name) in (select * from `blocking2`));
	
end //
-- -----------------------------------------
create procedure ghanari_ava14()
begin
	declare my_user_name varchar(20);
    call getLastLogin3 (my_user_name);
	
    select a.`ava_id`, a.`content`, count(liked)
	from `ava5` as a, `ava_like3` as l
    where l.`liked` = 1 
		and a.`ava_id` = l.`ava__id` 
        and (a.`user_name`, my_user_name) not in (select * from `blocking2`)
	group by (a.`ava_id`)
	order by count(`liked`) desc;
	
end //
-- -----------------------------------------
create procedure send_message2(
	in type_of_message int,
	in content varchar(256), 
	in ava_id int,
    in delivered_user_name varchar(20)
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3 (my_user_name);
	
    if type_of_message = 1 then
		insert into `message7` (`content`, `ava_id`, `sender_id`, `delivered_id`)
		select content, null, my_user_name, delivered_user_name
		where ((delivered_user_name, my_user_name) not in (select * from `blocking2`));
	else
		insert into `message7` (`content`, `ava_id`, `sender_id`, `delivered_id`)
		with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = ava_id)
		select  null, ava_id, my_user_name, delivered_user_name
		where ((delivered_user_name, my_user_name) not in (select * from `blocking2`)) 
			and not exists ( 
			select `u` from `id2username` 
				where (`id2username`.`u`, my_user_name) in (select * from `blocking2`)
			);
	end if;
    
end //
-- -----------------------------------------
create procedure get_message_from4(
	  in sb_user_name varchar(20)
)
begin
	declare my_user_name varchar(20);
    call getLastLogin3 (my_user_name);
	
    with abc_messages1 as (
	(
		with `id2username`(`u`) as (select `user_name` from `ava5` where `ava_id` = ava_id)
		select a.`user_name` as forwarded_from, a.`content`, m.`sender_id`, m.`delivered_id`, m.`send_date`
		from `ava5` as a
		inner join `message7` as m 
		on m.`delivered_id` = my_user_name and m.`sender_id` = sb_user_name and (m.`ava_id` = a.`ava_id`) 
        where not exists ( 
			select `u` from `id2username` 
				where (`id2username`.`u`, my_user_name) in (select * from `blocking2`)
			)
		)
	union
		select '--', `content`, `sender_id`, `delivered_id`, `send_date`
		from  `message7` as m
		where `content` is not null and m.`delivered_id` = my_user_name and m.`sender_id` = sb_user_name)
		
	select * from abc_messages1 order by `send_date` desc;

end //

-- -----------------------------------------
create procedure get_senders_list1()
begin
	declare my_user_name varchar(20);
    call getLastLogin3 (my_user_name);
	
    with abc_messages1 as (
	(
		select `sender_id`, m.`send_date`
		from `ava5` as a
		inner join `message7` as m 
		on m.`delivered_id` = my_user_name and (m.`ava_id` = a.`ava_id`) 
	)
	union
		select `sender_id`, `send_date`
		from  `message7` as m
		where `content` is not null and m.`delivered_id` = my_user_name
	)
	select (`sender_id`), `send_date` from abc_messages1 
    order by `send_date` desc;

end //
-- -----------------------------------------
create procedure get_hashtag_id4 (
	in hashtag varchar(6),
    out hashtag_id int
)
begin
	select `hashtId` into hashtag_id
    from `hashtag2`
    where `content` like hashtag
    limit 1;
    
end//

create trigger createHashtag_and_avaLog_after_ava after insert on `ava5` for each row 
begin
	declare hashtag_id int  default 8; 
	declare pos int default 1;
	declare rem varchar(252);
	declare del char(1); 
	declare hashtag_str varchar(6); 
   
	set rem = new.`content`;
	set del = '#';
    set pos = instr(rem, `del`);
    
    while pos > 0 do
        set rem = substring(rem, pos);
        set hashtag_str = left(rem, 6);
        call make_hashtag12(hashtag_str, hashtag_id);
        -- call get_hashtag_id4(hashtag_str, hashtag_id);
        call add_hashtag_to_ava(new.`ava_id`, hashtag_id);
		set rem = substring(rem, 7);
		set pos = instr(rem, `del`);
    end while;
    
    insert into `ava_log1` 
	values (NEW.`ava_id`, NEW.`user_name`, new.`send_date`);
    
end //
-- -----------------------------------------
create trigger after_create_account2 after insert
on `user`
for each row
insert into `create_account_log2` values (NEW.`user_name`, NEW.`membership_date`);//
-- -----------------------------------------
DELIMITER ;

drop trigger create_hashtag_after_ava;
show triggers from `new_ava`;

select * from `user`;
select * from `following`;
select * from `blocking2`;
select * from `ava5`;
select * from `hashtag2`;
select * from `ava_log1`;

call create_account2('a', 'b', 'c', 'c', '2020-09-08', 'sdf');   					-- **
-- create account a v asdf asdf 2010-09-03 sdfa
call getLastLogin2();		
call getLogins2();																	-- **
call loginIntoAccount3('abbcd', 'abbcd');											-- **
call follow2('abbcd');																-- **
call follow2('abcd');														
call follow2('a');
call unfollow1('ab');
call block1('ab');																	-- **
call block1('abc');								
call unblock1('ab');																-- **
call post_ava11("I am AB #ababa hello to everyone #goodl #luckk");					-- **
call get_my_ava13();																-- **
call get_followings_activities4();													-- **
call get_sb_activities6('abbcd');													-- **
call comment_on_ava2('hello to you bro', 20);										-- **
call get_comments_of_ava9(3);														-- **
call ava_with_specific_hashtag8('#labbb');											-- **
call like_ava2(19, 1); -- 1 means disliking ava										-- **
call like_ava2(16, 1);
call like_ava2(17, 1);
call like_ava2(3, 1); -- 0 means liking ava											-- **
call like_ava2(2, 0);
call likes_number3(8, 0);															-- **
call user_name_whom_like1(3, 0);													-- **
call ghanari_ava14();																-- **
call send_message2(1, "hi to you", null, 'cccc');									-- **
call send_message2(0, null, 9, 'abbcd');											-- **
call get_message_from4('c');														-- **
call get_senders_list1();															-- **

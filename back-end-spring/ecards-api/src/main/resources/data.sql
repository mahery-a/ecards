insert into theme(id, name, created_date, last_updated_date,is_deleted) 
values(10001,'Happy birthday', sysdate(), sysdate(),false);
insert into theme(id, name, created_date, last_updated_date,is_deleted) 
values(10002,'I love you', sysdate(), sysdate(),false);
insert into theme(id, name, created_date, last_updated_date,is_deleted) 
values(10003,'Happy new year', sysdate(), sysdate(),false);
insert into theme(id, name, created_date, last_updated_date,is_deleted) 
values(10004,'I miss you', sysdate(), sysdate(),false);
insert into theme(id, name, created_date, last_updated_date,is_deleted) 
values(10005,'Thank you', sysdate(), sysdate(),false);
insert into theme(id, name, created_date, last_updated_date,is_deleted) 
values(10006,'Merry christmas', sysdate(), sysdate(),false);

insert into user(id,username,password)
values(20001,'robert','password');
insert into user(id,username,password)
values(20002,'james','password');
insert into user(id,username,password)
values(20003,'annie','password');
insert into user(id,username,password)
values(20004,'admin','password');
insert into user(id,username,password)
values(20005,'john','password');

insert into user_roles(user_id,roles)
values(20001,'ROLE_USER');
insert into user_roles(user_id,roles)
values(20004,'ROLE_ADMIN');

insert into reviewV2(id,rating,description,theme_id)
values(50001,'FIVE', 'Best theme!',10001);
insert into reviewV2(id,rating,description,theme_id)
values(50002,'FOUR', 'Wonderful!',10001);
insert into reviewV2(id,rating,description,theme_id)
values(50003,'FIVE', 'Awesome!',10003);

insert into user_theme(user_id,theme_id)
values(20001,10001);
insert into user_theme(user_id,theme_id)
values(20002,10001);
insert into user_theme(user_id,theme_id)
values(20003,10001);
insert into user_theme(user_id,theme_id)
values(20001,10003);
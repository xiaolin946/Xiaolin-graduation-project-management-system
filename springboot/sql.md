##登录 SQL
select status,username,password
from sys_user
where status=? and username=? and password=?;

##创建用户 SQL
insert into 'sys_user'(status,username,password)
value(?,?,?);

##更新用户 SQL
update sys_user set ?=? where ?=?;

##删除用户 SQL
delete from sys_user where username=?;

##now() 是 SQL 内置函数，可以获取当前时间
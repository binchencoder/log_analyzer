#DROP USER 'root'@'localhost';
CREATE USER 'root'@'localhost' IDENTIFIED BY 'scrat';
CREATE DATABASE IF NOT EXISTS test DEFAULT CHARACTER SET utf8;
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,ALTER ON test.* TO root@'localhost';
FLUSH PRIVILEGES;
USE root;
#DROP TABLE IF EXISTS users;
CREATE TABLE users(
id INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
username VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户名',
password VARCHAR(50) NOT NULL DEFAULT '' COMMENT '密码',
status ENUM('1','0') NOT NULL DEFAULT '0' COMMENT '状态, 0 为普通用户, 1 为管理员',
INDEX(username),
PRIMARY KEY(id),
UNIQUE KEY(username)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT '用户帐号表';

#DROP TABLE IF EXISTS geoip_country;
CREATE TABLE geoip_country(
id INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
begin_ip VARCHAR(15) NOT NULL DEFAULT '' COMMENT '起始IP',
end_ip VARCHAR(15) NOT NULL DEFAULT '' COMMENT '结束IP',
begin_ip_sum INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '起始IP值',
end_ip_sum INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '结束IP值',
cn CHAR(2) NOT NULL DEFAULT '' COMMENT '国家简称',
country VARCHAR(50) NOT NULL DEFAULT '' COMMENT '国家名称',
PRIMARY KEY(id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT 'GeoIPLateCity';

load data local infile 'csv path' into table geoip_country 
character set utf8 
fields terminated by ',' 
optionally enclosed by '"' 
lines terminated by '\n' 
(begin_ip,end_ip,begin_ip_sum,end_ip_sum,cn,country);

#DROP TABLE IF EXISTS history;
CREATE TABLE history(
path VARCHAR(200) NOT NULL DEFAULT COMMENT '文件目录',
total_line INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '文件行数,记录被解析的行号',
md5_code CHAR(32) NOT NULL DEFAULT '' COMMENT 'MD5码',
status ENUM('0','1') NOT NULL DEFAULT '0' COMMENT '记录文件解析状态, 0代表解析失败, 1代表解析成功',
dt DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '插入时间',
PRIMARY KEY(path)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT '解释历史';

#DROP TABLE IF EXISTS log;
CREATE TABLE log(
ip VARCHAR(16) NOT NULL DEFAULT 'IP',
ip_sum INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'IP值',
dt DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '请求世间',
code INT NOT NULL DEFAULT 0 COMMENT '请求状态码',
request_length FLOAT NOT NULL DEFAULT 0 COMMENT '请求时长',
size INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '请求字节数',
api VARCHAR(100) NOT NULL DEFAULT '' COMMENT '请求api',
method VARCHAR(16) NOT NULL DEFAULT '' COMMENT '请求方法POST/GET',
file_type VARCHAR(16) NOT NULL DEFAULT '' COMMENT '文件类型',
os VARCHAR(16) NOT NULL DEFAULT '' COMMENT '操作系统'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT '原始日志';

insert ignore into log set ip='127.0.0.1',ip_sum=2130706433,dt='2013-12-12 12:12:12',code=200,request_length=60.001,size=4,api='/api',file_type='img',method='POST',os='Windows';

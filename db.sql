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
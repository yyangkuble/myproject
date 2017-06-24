-- --------------------------------------------------------
-- 主机:                           10.1.2.163
-- 服务器版本:                        10.0.8-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 test.app_request 结构
CREATE TABLE IF NOT EXISTS `app_request` (
  `id` char(36) NOT NULL DEFAULT '',
  `url` text,
  `starttime` bigint(20) DEFAULT NULL,
  `endtime` bigint(20) DEFAULT NULL,
  `threadid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  test.app_request 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `app_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `app_request` ENABLE KEYS */;

-- 导出  表 test.app_sql 结构
CREATE TABLE IF NOT EXISTS `app_sql` (
  `request_id` char(100) DEFAULT NULL,
  `sql_txt` text,
  `starttime` bigint(20) DEFAULT NULL,
  `endtime` bigint(20) DEFAULT NULL,
  `isasyn` varchar(5) DEFAULT NULL,
  `isredis` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  test.app_sql 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `app_sql` DISABLE KEYS */;
/*!40000 ALTER TABLE `app_sql` ENABLE KEYS */;

-- 导出  表 test.sys_api 结构
CREATE TABLE IF NOT EXISTS `sys_api` (
  `id` varchar(100) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `requestparam` text,
  `modename` varchar(100) DEFAULT NULL,
  `describle` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  test.sys_api 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_api` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_api` ENABLE KEYS */;

-- 导出  表 test.sys_log 结构
CREATE TABLE IF NOT EXISTS `sys_log` (
  `username` varchar(50) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remoteaddress` varchar(1000) DEFAULT NULL,
  `des` varchar(1000) DEFAULT NULL,
  `updatetime` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统log表';

-- 正在导出表  test.sys_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;

-- 导出  表 test.sys_menu 结构
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `orderby` double DEFAULT NULL,
  `isused` int(11) DEFAULT NULL,
  `parentid` bigint(20) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1024 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 正在导出表  test.sys_menu 的数据：~11 rows (大约)
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `name`, `des`, `orderby`, `isused`, `parentid`, `url`) VALUES
	(1001, '用户管理', '用户管理', 33.68764877319336, 1, 1008, 'userManager'),
	(1002, '系统设置', '系统设置', 7.9276299476623535, 1, 0, NULL),
	(1004, '菜单管理', '菜单管理', 3.5210700035095215, 1, 1002, 'meunManager'),
	(1006, 'SQL管理', 'SQL管理', 3.0522499084472656, 1, 1002, 'sqlManager'),
	(1008, '字典设置', '业务字典维护', 23.363910675048828, 1, 0, ''),
	(1010, '系统日志', '系统日志查看', 73.68765258789062, 1, 1002, 'log'),
	(1011, '角色管理', '角色管理', 13.699040412902832, 1, 1002, 'roleManager'),
	(1012, '开发学习案例', '开发学习案例', 93.68765258789062, 1, 1002, 'test'),
	(1019, '健康监控', '开发人员添加菜单，系统健康监控，添加菜单等', 49.375980377197266, 1, 0, ''),
	(1020, '性能监控', '请求使用时间和sql使用时间监控', NULL, 1, 1019, 'watch_requestAndSql'),
	(1021, '前端api接口测试', '前端工程师api接口测试', 113.68765258789062, 1, 1002, 'api_interface');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

-- 导出  表 test.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `des` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 正在导出表  test.sys_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `name`, `des`) VALUES
	(1000, '管理员', '最高权限'),
	(1001, '测试角色', '测试角色');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- 导出  表 test.sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `roleid` bigint(20) DEFAULT NULL,
  `menuid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色 菜单外联表';

-- 正在导出表  test.sys_role_menu 的数据：~11 rows (大约)
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`roleid`, `menuid`) VALUES
	(1000, 1006),
	(1000, 1004),
	(1000, 1011),
	(1000, 1010),
	(1000, 1012),
	(1000, 1021),
	(1000, 1001),
	(1000, 1003),
	(1000, 1007),
	(1000, 1009),
	(1000, 1020);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;

-- 导出  表 test.sys_sql 结构
CREATE TABLE IF NOT EXISTS `sys_sql` (
  `id` varchar(50),
  `sqlname` varchar(255) DEFAULT NULL,
  `sqlstr` varchar(2000) DEFAULT NULL,
  `max` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `modename` varchar(100) DEFAULT NULL COMMENT '模块名称',
  `requestparam` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sql';

-- 正在导出表  test.sys_sql 的数据：~19 rows (大约)
/*!40000 ALTER TABLE `sys_sql` DISABLE KEYS */;
INSERT INTO `sys_sql` (`id`, `sqlname`, `sqlstr`, `max`, `name`, `modename`, `requestparam`) VALUES
	('sqlselect', 'sql_select', 'select * from sys_sql where 1=1\n<%if( guanjianci ){%>\n	and(sqlname like \'%<%=guanjianci%>%\'\n	or sqlstr like \'%<%=guanjianci%>%\'\n	or name like \'%<%=guanjianci%>%\'\n	or modename like \'%<%=guanjianci%>%\')\n<%}%>\n<% if( modename ){ %>\n	and modename=#{ modename }\n<% } %>', 20, 'sql 查询通过条件', 'sql管理', '{"guanjianci":"管理","modename":"sql管理"}'),
	('sql_check_sqlname_uuid', 'sql_check_sqlname_uuid', 'select count(*) from sys_sql where sqlname=#{sqlname}', 30, '检查sqlname是否重复', 'sql管理', '{"sqlname":"sql_select"}'),
	('5399EA44-A061-4CB5-8CCF-2F2D74472532', 'menu_all', 'select * from sys_menu', 20, '菜单查询', '菜单管理', '""'),
	('0D9D580D-B8C7-48B4-B4AC-0049A85B7225', 'sql_get_id_by_sqlname', 'select id from sys_sql where sqlname=#{sqlname}', 30, '通过sqlname获取id,用于修改最大行数使用id', 'sql管理', '""'),
	('C8F970E1-8432-43E7-8174-C9AF62E60C6E', 'menu_by_parentid_and_userid', 'select c.* from sys_user_role a join sys_role_menu b on a.roleid=b.roleid join sys_menu c on b.menuid=c.id where c.parentid=#{parentid} and a.userid=#{userid}  order by c.orderby', 0, '按照userid查询子菜单', '用户管理', '""'),
	('5C16DEE7-6F2C-4277-BA5C-0F1DAE48EDA1', 'menu_get_max_order_code_by_parentid', 'select max(orderby)+20 from sys_menu where parentid=#{parentid}', 20, '通过父id获取子菜单最大的排序代码', '菜单管理', '""'),
	('1CE7F51B-D780-4C22-AD4D-917919CDE726', 'role_all', 'select * from sys_role', 0, '查询全部角色', '角色管理', '""'),
	('A96A8788-FCD9-4A3C-9D6B-38DC27830EED', 'menuid_by_roleid', 'select menuid from sys_role_menu where roleid=#{roleid}', 0, '通过角色id查询菜单列表', '菜单管理', '""'),
	('02CC6AD5-DEC3-4412-A1F3-14EC0D4F4C0E', 'delete_sys_role_menu', 'delete from sys_role_menu where roleid=#{roleid}', 20, '通过roleid删除所有菜单', '角色管理', '{"roleid":"123"}'),
	('B35470B3-307E-4CCA-B18F-7CC31FBBCBE5', 'user_select_by_where', 'select * from t_user <%=where%> order by addtime desc', 20, '通过条件查询用户', '用户管理', '""'),
	('194FB05A-027E-4490-8D17-BDAE0C4C94EA', 'user_check_username_isused', 'select count(*) from t_user where username=#{username}', 20, '检查用户名是否重复', '用户管理', '""'),
	('272696A8-602A-4022-A288-D2D2719BC4C2', 'role_slect_by_userid', 'select roleid from sys_user_role where userid=#{userid}', 20, '通过userid查询角色', '用户管理', '""'),
	('33DE660B-F0B5-49E8-BBBF-41EA05EBE455', 'test_select_by_where', 'select * from sys_test \n<% if( guanjianci ){ %>\n	where username like \'%<%=guanjianci %>%\' or tel like \'%<%=guanjianci %>%\' or name like \'%<%=guanjianci %>%\'\n<% } %>\norder by addtime desc', 20, '测试查询通过条件', '案例', '{"guanjianci":"test"}'),
	('19DCA181-71C5-4F3B-A14A-AF7ACADB3BE1', 'test_check_username_isused', 'select count(*) from sys_test where username=#{username}', 20, '查询测试表username是否重复', '案例', '""'),
	('201701171736120210', 'log_select_by_where', 'select * from sys_log <%=where%> order by updatetime desc', 20, '通过条件查询log', '系统日志', '""'),
	('201701171826065240', 'menu_by_parentid', 'select * from sys_menu where parentid=#{parentid} order by orderby', 20, '通过parentid查询菜单', '菜单管理', '""'),
	('201703231345064150', 'select_all_modename', 'select distinct modename from sys_sql ', 20, '查询所有sql模块名称', 'sql管理', '""'),
	('201703242105399460', 'watch_request', 'select d.id as requestid,d.url,d.usetime,d.starttime,d.sqlallusetime,d.sqlcount,(d.usetime-d.sqlallusetime) as javausetime from (select c.id,c.url,c.usetime,c.starttime,sum(sqlusetime) as sqlallusetime,count(sqlusetime) as sqlcount from (select a.id,a.url,a.starttime,(a.endtime-a.starttime) as usetime,(b.endtime-b.starttime) as sqlusetime from app_request a join app_sql b on a.id=b.request_id) c group by c.url,c.usetime,c.id) d', 20, '查询请求监控', '性能监控', '""'),
	('201703251838229420', 'select_sql_by_requestid', 'select sql_txt,starttime,endtime,(endtime-starttime) as usetime,isasyn,isredis from app_sql where request_id =#{requestid}', 20, '通过requestid查询所有sql', '性能监控', '{"requestid":"201703242342475550"}'),
	('201703311352056310', 'select_sql_by_sqlname', 'select * from sys_sql where sqlname=#{sqlname}', 20, '通过sqlname获取sql模板', 'api接口测试', '{"sqlname":"sql_select"}'),
	('201703312317559120', 'select_api_by_modename', 'select * from sys_api where modename=#{modename}', 20, '通过modename获取api', 'api接口测试', '{"modename":"用户管理"}');
/*!40000 ALTER TABLE `sys_sql` ENABLE KEYS */;

-- 导出  表 test.sys_test 结构
CREATE TABLE IF NOT EXISTS `sys_test` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `islock` int(11) DEFAULT NULL,
  `lastsetpasswordtime` varchar(100) DEFAULT NULL,
  `errorpasswordtimes` int(11) DEFAULT NULL,
  `appclienttype` varchar(50) DEFAULT NULL COMMENT '用于推送消息ios or andriod ',
  `approle` varchar(50) DEFAULT NULL COMMENT 'app的角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  test.sys_test 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `sys_test` DISABLE KEYS */;
INSERT INTO `sys_test` (`id`, `name`, `username`, `password`, `tel`, `addtime`, `islock`, `lastsetpasswordtime`, `errorpasswordtimes`, `appclienttype`, `approle`) VALUES
	('40206791-4596-4319-bd4f-a1f8cbe53490', '士大夫撒旦', '是大方的说法', 'password', '士大夫撒旦', '2017-04-02 22:28:49', NULL, 'newuser', NULL, NULL, 'service'),
	('47e1305b-83df-4194-b260-9c09d5b37a50', 'dfdf', 'dddf', 'password', 'dfdf', '2017-03-29 18:04:41', 0, 'newuser', 0, NULL, 'engineer'),
	('5a67cda4-baa0-4bc7-ac25-fdc98b00d027', 'df', 'dfdsd', 'password', 'dfdg', '2017-03-31 18:05:26', 0, 'newuser', 0, NULL, 'service'),
	('6f780f40-ac44-47ac-ad24-e3a1bf54a2b3', '撒旦法士大夫', '是对方是范德萨', 'password', '士大夫撒旦法', '2017-04-02 23:26:21', 0, 'newuser', 0, NULL, 'engineer'),
	('762d40bc-b705-4870-8c6e-5474e424bb2e', '撒旦法师的', 'asdfsd123432', 'password', '撒旦飞洒地方', '2017-04-02 23:01:36', 0, 'newuser', 0, NULL, 'service'),
	('8559df0d-dc08-4585-bf34-3bf34416cc6f', '撒旦飞洒地方23', '士大夫撒旦法123', 'password', '士大夫撒', '2017-04-02 22:59:23', 0, 'newuser', 0, NULL, 'engineer'),
	('8dd76515-0566-4d11-9c1d-6f7dd4911742', '速度速度', '速度速度', 'password', '是多少', '2017-04-02 22:32:30', NULL, 'newuser', NULL, NULL, 'service'),
	('a9eeea7a-b300-46db-9acb-4f1083fc3dfd', '所得税法国', '速度速度速度速度', 'password', '按时打算', '2017-04-02 22:54:17', 0, 'newuser', 0, NULL, 'service'),
	('d7f33b08-1a0c-4cd7-abc9-5808aaf2180d', 'test1', 'test1', 'password', 'test1', '2017-03-27 22:58:58', 0, 'newuser', 0, NULL, 'service');
/*!40000 ALTER TABLE `sys_test` ENABLE KEYS */;

-- 导出  表 test.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `userid` bigint(20) DEFAULT NULL,
  `roleid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色外联表';

-- 正在导出表  test.sys_user_role 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`userid`, `roleid`) VALUES
	(1005, 1000),
	(1005, 1001);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

-- 导出  表 test.t_user 结构
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `islock` int(11) DEFAULT NULL,
  `lastsetpasswordtime` varchar(100) DEFAULT NULL,
  `errorpasswordtimes` int(11) DEFAULT NULL,
  `appclienttype` varchar(50) DEFAULT NULL COMMENT '用于推送消息ios or andriod ',
  `approle` varchar(50) DEFAULT NULL COMMENT 'app的角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=utf8;

-- 正在导出表  test.t_user 的数据：~7 rows (大约)
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`id`, `name`, `username`, `password`, `tel`, `addtime`, `islock`, `lastsetpasswordtime`, `errorpasswordtimes`, `appclienttype`, `approle`) VALUES
	(1000, 'test', 'test', 'password', 'test', '2017-01-15 16:01:46', 0, 'newuser', 0, NULL, 'service'),
	(1001, '宋荣洋', 'yyang', 'password', '18612290350', '2017-01-14 14:45:14', 0, 'newuser', 0, NULL, ''),
	(1005, '管理员', 'admin', 'admin', '', '2017-01-17 13:48:04', 0, 'newuser', 0, NULL, '');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

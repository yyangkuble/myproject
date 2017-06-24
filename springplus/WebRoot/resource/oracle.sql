--菜单数据初始化
INSERT INTO sys_menu (id, name, des, orderby, isused, parentid, url) VALUES(1001, '用户管理', '用户管理', 33.68764877319336, 1, 1008, 'plus/userManager');
INSERT INTO sys_menu (id, name, des, orderby, isused, parentid, url) VALUES(1002, '系统设置', '系统设置', 7.9276299476623535, 1, 0, NULL);
INSERT INTO sys_menu (id, name, des, orderby, isused, parentid, url) VALUES(1004, '菜单管理', '菜单管理', 3.5210700035095215, 1, 1002, 'plus/meunManager');
INSERT INTO sys_menu (id, name, des, orderby, isused, parentid, url) VALUES(1008, '字典设置', '业务字典维护', 23.363910675048828, 1, 0, '');
INSERT INTO sys_menu (id, name, des, orderby, isused, parentid, url) VALUES(1010, '系统日志', '系统日志查看', 73.68765258789062, 1, 1002, 'plus/log');
INSERT INTO sys_menu (id, name, des, orderby, isused, parentid, url) VALUES(1011, '角色管理', '角色管理', 13.699040412902832, 1, 1002, 'plus/roleManager');
INSERT INTO sys_menu (id, name, des, orderby, isused, parentid, url) VALUES(1012, '开发学习案例', '开发学习案例', 93.68765258789062, 1, 1002, 'plus/test');
INSERT INTO sys_menu (id, name, des, orderby, isused, parentid, url) VALUES(1019, '健康监控', '开发人员添加菜单，系统健康监控，添加菜单等', 49.375980377197266, 1, 0, '');
INSERT INTO sys_menu (id, name, des, orderby, isused, parentid, url) VALUES(1020, '性能监控', '请求使用时间和sql使用时间监控', NULL, 1, 1019, 'plus/watch_requestAndSql');
INSERT INTO sys_menu (id, name, des, orderby, isused, parentid, url) VALUES(1021, '前端api接口测试', '前端工程师api接口测试', 113.68765258789062, 1, 1002, 'plus/api_interface');

--角色初始化
INSERT INTO sys_role (id, name, des) VALUES(1000, '管理员', '最高权限');
--角色对应菜单权限   初始化
INSERT INTO sys_role_menu (roleid, menuid) VALUES(1000, 1004);
INSERT INTO sys_role_menu (roleid, menuid) VALUES(1000, 1011);
INSERT INTO sys_role_menu (roleid, menuid) VALUES(1000, 1010);
INSERT INTO sys_role_menu (roleid, menuid) VALUES(1000, 1012);
INSERT INTO sys_role_menu (roleid, menuid) VALUES(1000, 1021);
INSERT INTO sys_role_menu (roleid, menuid) VALUES(1000, 1001);
INSERT INTO sys_role_menu (roleid, menuid) VALUES(1000, 1003);
INSERT INTO sys_role_menu (roleid, menuid) VALUES(1000, 1007);
INSERT INTO sys_role_menu (roleid, menuid) VALUES(1000, 1009);
INSERT INTO sys_role_menu (roleid, menuid) VALUES(1000, 1020);

--用户对应的角色初始化
INSERT INTO sys_user_role (userid, roleid) VALUES(1005, 1000);
INSERT INTO sys_user_role (userid, roleid) VALUES(1005, 1001);
--用户初始化
INSERT INTO t_user (id, name, username, password, tel, addtime, islock, lastsetpasswordtime, errorpasswordtimes, appclienttype, approle) VALUES(1005, '管理员', 'admin', 'admin', '', '2017-01-17 13:48:04', 0, 'newuser', 0, NULL, '');
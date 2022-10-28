-- 初始化管理员账户
INSERT INTO `zacboot`.`sys_user`
(`uuid`, `username`, `password`, `real_name`, `avatar`, `nickname`, `mail`, `phone`, `create_user`, `create_time`,
 `update_user`, `update_time`, `deleted`)
VALUES ('e9ca23d68d884d4ebb19d07889727dae', 'zacboot',
        '$2a$10$VZ0DkrPjMFScvPyz4wVpneazIGN4rfK57OY0baiQ.m1FF5t9JcRpG', '管理员',
        'http://minio.jeecg.com/otatest/temp/lgo33_1583397323099.png',
        '管理员', 'zacboot@163.com', '18611111111', 'A01', now(), 'A01', now(), false);

-- 初始化部门表
INSERT INTO `zacboot`.`sys_dept` (`uuid`, `parent_id`, `depart_name`, `description`,
                                   `mobile`, `fax`, `address`, `memo`, `status`, `deleted`, `create_user`,
                                   `create_time`,
                                   `update_user`, `update_time`)
VALUES ('4f1765520d6346f9bd9c79e2479e5b12', '', '上海飞云科技', NULL, NULL, NULL, NULL, NULL, '1', 0, 'zacboot',
        now(), 'zacboot', now());

INSERT INTO `zacboot`.`sys_dept` (`uuid`, `parent_id`, `depart_name`, `description`,
                                   `mobile`, `fax`, `address`, `memo`, `status`, `deleted`, `create_user`,
                                   `create_time`,
                                   `update_user`, `update_time`)
VALUES ('5159cde220114246b045e574adceafe9', '4f1765520d6346f9bd9c79e2479e5b12', '研发部', NULL, NULL, NULL,
        NULL, NULL, '1', 0, 'zacboot', now(), 'zacboot', now());

INSERT INTO `zacboot`.`sys_dept` (`uuid`, `parent_id`, `depart_name`, `description`,
                                   `mobile`, `fax`, `address`, `memo`, `status`, `deleted`, `create_user`,
                                   `create_time`,
                                   `update_user`, `update_time`)
VALUES ('5159cde220114246b045e574adceafe8', '4f1765520d6346f9bd9c79e2479e5b12', '财务部', NULL, NULL, NULL,
        NULL, NULL, '1', 0, 'zacboot', now(), 'zacboot', now());

INSERT INTO `zacboot`.`sys_dept` (`uuid`, `parent_id`, `depart_name`, `description`,
                                   `mobile`, `fax`, `address`, `memo`, `status`, `deleted`, `create_user`,
                                   `create_time`,
                                   `update_user`, `update_time`)
VALUES ('5159cde220114246b045e574adceafe7', '4f1765520d6346f9bd9c79e2479e5b12', '人事部', NULL, NULL, NULL,
        NULL, NULL, '1', 0, 'zacboot', now(), 'zacboot', now());

-- 初始化角色
INSERT INTO `zacboot`.`sys_role` (`uuid`, `role_name`, `role_code`, `description`, `create_user`, `create_time`,
                                   `update_user`, `update_time`)
VALUES ('f6817f48af4fb3af11b9e8bf182f618b', '管理员', 'admin', '管理员', 'auto', now(), 'auto',
        now());


-- 初始化用户 ~ 部门关联表
INSERT INTO `zacboot`.`sys_user_dept` (`uuid`, `user_uuid`, `dept_uuid`,create_user,create_time,update_user,update_time,deleted)
VALUES ('1255ebc5dfc611eaa09238d5477a5fc1', 'e9ca23d68d884d4ebb19d07889727dae',
        '4f1765520d6346f9bd9c79e2479e5b12', 'init', now(), 'init', now(), 0);

-- 初始化用户 ~ 角色关联表
INSERT INTO `zacboot`.`sys_user_role` (`uuid`, `user_uuid`, `role_uuid`,create_user,create_time,update_user,update_time,deleted)
VALUES ('42448bdedfc611eaa09238d5477a5fc1', 'e9ca23d68d884d4ebb19d07889727dae',
        'f6817f48af4fb3af11b9e8bf182f618b', 'init', now(), 'init', now(), 0);


-- 初始化权限菜单表
INSERT INTO zacboot.sys_permission  VALUES (null, '首页', null, null, 'home', 'Home', '/dashboard/analysis', null, 0, 0, true, true, null, 0, true, null, null, 1, 'homepage', 'admin', '2021-09-20 22:02:03', 'admin', '2021-09-20 22:02:03', false);
INSERT INTO zacboot.sys_permission  VALUES (null, '系统管理', null, null, 'Setting', '/system', '/system', null, 1, 0, false, true, null, 0, false, null, null, 2, 'd7d6e2e4e2934f2c9385a623fd98c6f3', 'admin', '2021-09-20 22:02:03', 'admin', '2021-09-20 22:02:03', false);
INSERT INTO zacboot.sys_permission  VALUES (null, '个人页', null, null, 'user', 'layouts/RouteView', '/account', null, 2, 0, false, true, false, 0, true, null, false, 3, '717f6bee46f44a3897eca9abd6e2ec44', 'admin', '2021-09-20 22:02:04', 'admin', '2021-09-20 22:02:04', false);
INSERT INTO zacboot.sys_permission  VALUES (null, '结果页', null, null, 'setting', 'layouts/PageView', '/result', null, 3, 0, false, true, null, 0, true, null, null, 4, '2e42e3835c2b44ec9f7bc26c146ee531', 'admin', '2021-09-20 22:02:04', 'admin', '2021-09-20 22:02:04', false);
INSERT INTO zacboot.sys_permission  VALUES (null, '异常页', null, null, 'warning', 'layouts/RouteView', '/exception', null, 4, 0, false, true, null, 0, true, null, null, 5, 'c65321e57b7949b7a975313220de0422', 'admin', '2021-09-20 22:02:04', 'admin', '2021-09-20 22:02:04', false);
INSERT INTO zacboot.sys_permission  VALUES ('d7d6e2e4e2934f2c9385a623fd98c6f3', '角色管理', null, null, 'HelpFilled', 'system/Roles', '/system/roles', null, 1, 1, true, true, false, 0, false, null, false, 6, '190c2b43bec6a5f7a4194a85db67d96a', 'admin', '2021-09-20 22:02:04', 'admin', '2021-09-20 22:02:04', false);
INSERT INTO zacboot.sys_permission  VALUES ('d7d6e2e4e2934f2c9385a623fd98c6f3', '用户管理', null, null, 'UserFilled', 'system/Users', '/system/users', null, 1, 1, false, true, false, 0, false, null, false, 7, '3f915b2769fc80648e92d04e84ca059d', 'admin', '2021-09-20 22:02:04', 'admin', '2021-09-20 22:02:04', false);
INSERT INTO zacboot.sys_permission  VALUES ('d7d6e2e4e2934f2c9385a623fd98c6f3', '部门管理', null, null, 'Promotion', 'system/DepartList', '/system/departList', null, 1, 1, false, true, false, 0, false, null, false, 8, '45c966826eeff4c99b8f8ebfe74511fc', 'admin', '2021-09-20 22:02:05', 'admin', '2021-09-20 22:02:05', false);
INSERT INTO zacboot.sys_permission  VALUES ('d7d6e2e4e2934f2c9385a623fd98c6f3', '菜单管理', null, null, 'Menu', 'system/Menus', '/system/menus', null, 1, 1, true, true, false, 0, false, null, false, 9, '54dd5457a3190740005c1bfec55b1c34', 'admin', '2021-09-20 22:02:05', 'admin', '2021-09-20 22:02:05', false);
INSERT INTO zacboot.sys_permission  VALUES ('717f6bee46f44a3897eca9abd6e2ec44', '个人设置', null, null, null, 'account/settings/Index', '/account/settings/index', null, 2, 1, false, true, null, 0, false, null, null, 10, '6e73eb3c26099c191bf03852ee1310a1', 'admin', '2021-09-20 22:02:05', 'admin', '2021-09-20 22:02:05', false);
INSERT INTO zacboot.sys_permission  VALUES ('717f6bee46f44a3897eca9abd6e2ec44', '工作台', null, null, null, 'dashboard/Workplace', '/dashboard/workplace', null, 2, 1, true, true, null, 0, false, null, null, 11, '8fb8172747a78756c11916216b8b8066', 'admin', '2021-09-20 22:02:11', 'admin', '2021-09-20 22:02:11', false);
INSERT INTO zacboot.sys_permission  VALUES ('717f6bee46f44a3897eca9abd6e2ec44', '个人中心', null, null, null, 'account/center/Index', '/account/center', null, 2, 1, true, true, null, 0, false, null, null, 12, 'd86f58e7ab516d3bc6bfb1fe10585f97', 'admin', '2021-09-20 22:02:11', 'admin', '2021-09-20 22:02:11', false);
INSERT INTO zacboot.sys_permission  VALUES ('2e42e3835c2b44ec9f7bc26c146ee531', '成功', null, null, null, 'result/Success', '/result/success', null, 3, 1, true, true, null, 0, false, null, null, 13, '00a2a0ae65cdca5e93209cdbde97cbe6', 'admin', '2021-09-20 22:02:12', 'admin', '2021-09-20 22:02:12', false);
INSERT INTO zacboot.sys_permission  VALUES ('2e42e3835c2b44ec9f7bc26c146ee531', '失败', null, null, null, 'result/Error', '/result/fail', null, 3, 1, true, true, null, 0, false, null, null, 14, '13212d3416eb690c2e1d5033166ff47a', 'admin', '2021-09-20 22:02:12', 'admin', '2021-09-20 22:02:12', false);
INSERT INTO zacboot.sys_permission  VALUES ('c65321e57b7949b7a975313220de0422', '403', null, null, null, 'exception/403', '/exception/403', null, 4, 1, true, true, null, 0, false, null, null, 15, '65a8f489f25a345836b7f44b1181197a', 'admin', '2021-09-20 22:02:12', 'admin', '2021-09-20 22:02:12', false);
INSERT INTO zacboot.sys_permission  VALUES ('c65321e57b7949b7a975313220de0422', '500', null, null, null, 'exception/500', '/exception/500', null, 4, 1, true, true, null, 0, false, null, null, 16, 'b4dfc7d5dd9e8d5b6dd6d4579b1aa559', 'admin', '2021-09-20 22:02:12', 'admin', '2021-09-20 22:02:12', false);
INSERT INTO zacboot.sys_permission  VALUES ('c65321e57b7949b7a975313220de0422', '404', null, null, null, 'exception/404', '/exception/404', null, 4, 1, true, true, null, 0, false, null, null, 17, 'd2bbf9ebca5a8fa2e227af97d2da7548', 'admin', '2021-09-20 22:02:12', 'admin', '2021-09-20 22:02:12', false);
INSERT INTO zacboot.sys_permission  VALUES ('3f915b2769fc80648e92d04e84ca059d', '用户管理详情', 'detail', '1', null, '', '', '', 0, 2, true, true, false, 0, false, '1', false, 18, '12147432705042261231232132133818', 'admin', '2022-01-08 17:49:00', null, null, false);
INSERT INTO zacboot.sys_permission  VALUES ('3f915b2769fc80648e92d04e84ca059d', '用户管理删除', 'delete', '1', null, '', '', '', 0, 2, true, true, false, 0, false, '1', false, 19, '12147434438212567069999999999999', 'admin', '2022-01-08 17:49:00', null, null, false);
INSERT INTO zacboot.sys_permission  VALUES ('3f915b2769fc80648e92d04e84ca059d', '用户管理冻结', 'frozen', '1', null, '', '', '', 0, 2, true, true, false, 0, false, '1', false, 20, '12147435023569633290000000000000', 'admin', '2022-01-08 17:49:00', null, null, false);
INSERT INTO zacboot.sys_permission  VALUES ('3f915b2769fc80648e92d04e84ca059d', '用户添加', 'add', '1', null, '', '', '', 0, 2, true, true, false, 0, false, '1', false, 21, '12147435604229079060000000000000', 'admin', '2022-01-08 17:49:00', null, null, false);
INSERT INTO zacboot.sys_permission  VALUES ('3f915b2769fc80648e92d04e84ca059d', '用户管理密码', 'password', '1', null, null, null, null, 0, 2, true, true, false, 0, false, '1', false, 22, 'ajhfdh2k34h23kj4h2iu54241234234d', 'admin', '2022-01-08 19:02:01', null, null, false);
INSERT INTO zacboot.sys_permission  VALUES ('3f915b2769fc80648e92d04e84ca059d', '用户编辑', 'edit', '1', null, null, null, null, 0, 2, true, true, false, 0, false, '1', false, 23, '1sdddddddddddddddd12312312323555', 'admin', '2022-01-08 19:12:22', null, null, false);
INSERT INTO zacboot.sys_permission  VALUES ('190c2b43bec6a5f7a4194a85db67d96a', '角色添加', 'add', '1', null, null, null, null, 0, 2, true, true, false, 0, false, '1', false, 24, '1sddddddddddddccdd12312312323555', 'admin', '2022-01-08 19:12:22', null, null, false);

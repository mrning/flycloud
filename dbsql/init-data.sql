-- 初始化管理员账户
INSERT INTO `zacboot`.`sys_user`
(`uuid`, `username`, `password`, `real_name`, `avatar`, `nickname`, `mail`, `phone`, `create_user`, `create_time`,
 `update_user`, `update_time`, `deleted`)
VALUES ('superadmin', 'zacboot',
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
VALUES ('1255ebc5dfc611eaa09238d5477a5fc1', 'superadmin',
        '4f1765520d6346f9bd9c79e2479e5b12', 'init', now(), 'init', now(), 0);

-- 初始化用户 ~ 角色关联表
INSERT INTO `zacboot`.`sys_user_role` (`uuid`, `user_uuid`, `role_uuid`, create_user, create_time, update_user,
                                       update_time, deleted)
VALUES ('42448bdedfc611eaa09238d5477a5fc1', 'superadmin',
        'f6817f48af4fb3af11b9e8bf182f618b', 'init', now(), 'init', now(), 0);


-- 初始化权限菜单表
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('homepage', null, '首页', null, 'HomeFilled', 'Home', 'Home', null, 0, 0, true, 'admin', '2021-09-20 22:02:03',
        'admin', '2021-09-20 22:02:03', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('d7d6e2e4e2934f2c9385a623fd98c6f3', null, '系统管理', null, 'Setting', '/system', '/system', null, 1, 0, false,
        'admin', '2021-09-20 22:02:03', 'admin', '2021-09-20 22:02:03', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('190c2b43bec6a5f7a4194a85db67d96a', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '角色管理', null, 'HelpFilled',
        'system/Roles', '/system/roles', null, 11, 1, false, 'admin', '2021-09-20 22:02:04', 'admin',
        '2021-09-20 22:02:04', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('3f915b2769fc80648e92d04e84ca059d', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '用户管理', null, 'UserFilled',
        'system/Users', '/system/users', null, 10, 1, false, 'admin', '2021-09-20 22:02:04', 'admin',
        '2021-09-20 22:02:04', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('45c966826eeff4c99b8f8ebfe74511fc', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '部门管理', null, 'Promotion',
        'system/DepartList', '/system/departList', null, 12, 1, false, 'admin', '2021-09-20 22:02:05', 'admin',
        '2021-09-20 22:02:05', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('54dd5457a3190740005c1bfec55b1c34', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '菜单管理', null, 'Menu',
        'system/Menus', '/system/menus', null, 13, 1, false, 'admin', '2021-09-20 22:02:05', 'admin',
        '2021-09-20 22:02:05', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('6e73eb3c26099c191bf03852ee1310a1', '717f6bee46f44a3897eca9abd6e2ec44', '个人设置', null, null,
        'account/settings/Index', '/account/settings/index', null, 2, 1, false, 'admin', '2021-09-20 22:02:05', 'admin',
        '2021-09-20 22:02:05', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('8fb8172747a78756c11916216b8b8066', '717f6bee46f44a3897eca9abd6e2ec44', '工作台', null, null,
        'dashboard/Workplace', '/dashboard/workplace', null, 2, 1, false, 'admin', '2021-09-20 22:02:11', 'admin',
        '2021-09-20 22:02:11', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('d86f58e7ab516d3bc6bfb1fe10585f97', '717f6bee46f44a3897eca9abd6e2ec44', '个人中心', null, null,
        'account/center/Index', '/account/center', null, 2, 1, false, 'admin', '2021-09-20 22:02:11', 'admin',
        '2021-09-20 22:02:11', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('12147434438212567069999999999999', '3f915b2769fc80648e92d04e84ca059d', '用户管理删除', 'delete', null, null,
        null, null, 0, 2, false, 'admin', '2022-01-08 17:49:00', null, null, false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('12147435604229079060000000000000', '3f915b2769fc80648e92d04e84ca059d', '用户添加', 'add', null, null, null,
        null, 0, 2, false, 'admin', '2022-01-08 17:49:00', null, null, false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('ajhfdh2k34h23kj4h2iu54241234234d', '3f915b2769fc80648e92d04e84ca059d', '用户管理密码', 'password', null, null,
        null, null, 0, 2, false, 'admin', '2022-01-08 19:02:01', null, null, false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('1sdddddddddddddddd12312312323555', '3f915b2769fc80648e92d04e84ca059d', '用户编辑', 'edit', null, null, null,
        null, 0, 2, false, 'admin', '2022-01-08 19:12:22', null, null, false);

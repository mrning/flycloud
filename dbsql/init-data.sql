-- 初始化管理员账户
INSERT INTO `zacboot`.`sys_user`
(`uuid`, `username`, `password`, `real_name`, `avatar`, `nickname`, `mail`, `phone`, `create_user`, `create_time`,
 `update_user`, `update_time`, `deleted`)
VALUES ('superadmin', 'zacboot',
        '$2a$10$VZ0DkrPjMFScvPyz4wVpneazIGN4rfK57OY0baiQ.m1FF5t9JcRpG', '管理员',
        'http://minio.jeecg.com/otatest/temp/lgo33_1583397323099.png',
        '管理员', 'zacboot@163.com', '18611111111', 'A01', now(), 'A01', now(), false);

-- 初始化部门表
INSERT INTO `zacboot`.`sys_dept` (`uuid`, `parent_uuid`, `depart_name`, `description`,
                                   `mobile`, `leader_uuid`, `dept_address`, `deleted`, `create_user`,
                                   `create_time`,
                                   `update_user`, `update_time`)
VALUES ('mainDept', '', '上海飞云科技', NULL, NULL, NULL, NULL, 0, 'zacboot',
        now(), 'zacboot', now());

-- 初始化角色
INSERT INTO `zacboot`.`sys_role` (`uuid`, `role_name`, `role_code`, `description`, `create_user`, `create_time`,
                                  `update_user`, `update_time`)
VALUES ('adminRole', '管理员', 'admin', '管理员', 'auto', now(), 'auto',
        now());


-- 初始化用户 ~ 部门关联表
INSERT INTO `zacboot`.`sys_user_dept` (`uuid`, `user_uuid`, `dept_uuid`, create_user, create_time, update_user,
                                       update_time, deleted)
VALUES ('1255ebc5dfc611eaa09238d5477a5fc1', 'superadmin',
        'mainDept', 'init', now(), 'init', now(), 0);

-- 初始化用户 ~ 角色关联表
INSERT INTO `zacboot`.`sys_user_role` (`uuid`, `user_uuid`, `role_uuid`, create_user, create_time, update_user,
                                       update_time, deleted)
VALUES ('42448bdedfc611eaa09238d5477a5fc1', 'superadmin',
        'adminRole', 'init', now(), 'init', now(), 0);

INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('homepage', null, '首页', null, 'HomeFilled', 'Home', 'Home', null, 0, 0, false, 'admin', '2021-09-20 22:02:03',
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
VALUES ('12147434438212567069999999999999', '3f915b2769fc80648e92d04e84ca059d', '用户删除', 'del', null, null,
        '/system/users', null, 0, 2, false, 'admin', '2022-01-08 17:49:00', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('12147435604229079060000000000000', '3f915b2769fc80648e92d04e84ca059d', '用户添加', 'add', null, null,
        '/system/users', null, 0, 2, false, 'admin', '2022-01-08 17:49:00', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('123123123223ab516d3bc6bf12323555', '3f915b2769fc80648e92d04e84ca059d', '用户编辑', 'edit', null, null,
        '/system/users', null, 0, 2, false, 'admin', '2022-01-08 19:12:22', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('8e7aby2k34h23kj4h2iu542412342341', '190c2b43bec6a5f7a4194a85db67d96a', '角色添加', 'add', null, null,
        '/system/roles', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('c97b0b7e6f0d11eda70138d5477a5fc1', '190c2b43bec6a5f7a4194a85db67d96a', '角色编辑', 'edit', null, null,
        '/system/roles', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('c98830f66f0d11eda70138d5477a5fc1', '190c2b43bec6a5f7a4194a85db67d96a', '角色删除', 'del', null, null,
        '/system/roles', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('c992b04e6f0d11eda70138d5477a5fc1', '45c966826eeff4c99b8f8ebfe74511fc', '部门添加', 'add', null, null,
        '/system/departList', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('c99fb5176f0d11eda70138d5477a5fc1', '45c966826eeff4c99b8f8ebfe74511fc', '部门编辑', 'edit', null, null,
        '/system/departList', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('c9b5d15d6f0d11eda70138d5477a5fc1', '45c966826eeff4c99b8f8ebfe74511fc', '部门删除', 'del', null, null,
        '/system/departList', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('c992b04e6f0d11eda70138d5477a5fc0', '54dd5457a3190740005c1bfec55b1c34', '菜单添加', 'add', null, null,
        '/system/menus', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('c99fb5176f0d11eda70138d5477a5fc2', '54dd5457a3190740005c1bfec55b1c34', '菜单编辑', 'edit', null, null,
        '/system/menus', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('c9b5d15d6f0d11eda70138d5477a5fc3', '54dd5457a3190740005c1bfec55b1c34', '菜单添加子菜单', 'addChild', null,
        null, '/system/menus', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01',
        false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('c9b5d15d6f0d11eda70138d5477a5fc4', '54dd5457a3190740005c1bfec55b1c34', '子菜单添加按钮', 'addBtn', null, null,
        '/system/menus', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01', false);
INSERT INTO zacboot.sys_permission (uuid, parent_uuid, name, code, icon, component, url, redirect, sort_no, menu_type,
                                    hidden, create_user, create_time, update_user, update_time, deleted)
VALUES ('c9b5d15d6f0d11eda70138d5477a5fc5', '54dd5457a3190740005c1bfec55b1c34', '菜单(按钮)删除', 'del', null, null,
        '/system/menus', null, 0, 2, false, 'admin', '2022-01-08 19:02:01', 'admin', '2022-01-08 19:02:01', false);
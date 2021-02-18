-- 初始化管理员账户
INSERT INTO `flycloud`.`sys_user`
(`uuid`, `username`, `password`, `realname`, `avatar`, `nickname`, `mail`, `phone`, `create_user`, `create_time`,
 `update_user`, `update_time`, `deleted`)
VALUES ('e9ca23d68d884d4ebb19d07889727dae', 'flycloud', 'cb362cfeefbf3d8d', '管理员',
        'http://minio.jeecg.com/otatest/temp/lgo33_1583397323099.png',
        '管理员', 'flycloud@163.com', '18611111111', 'A01', now(), 'A01', now(), false);

-- 初始化部门表
INSERT INTO `flycloud`.`sys_dept` (`uuid`, `parent_id`, `depart_name`, `description`,
                                   `mobile`, `fax`, `address`, `memo`, `status`, `deleted`, `create_user`, `create_time`,
                                   `update_user`, `update_time`)
VALUES ('4f1765520d6346f9bd9c79e2479e5b12', '', '上海飞云科技', NULL, NULL, NULL, NULL, NULL, '1', 0, 'flycloud',
        now(), 'flycloud', now());

INSERT INTO `flycloud`.`sys_dept` (`uuid`, `parent_id`, `depart_name`, `description`,
                                   `mobile`, `fax`, `address`, `memo`, `status`, `deleted`, `create_user`, `create_time`,
                                   `update_user`, `update_time`)
VALUES ('5159cde220114246b045e574adceafe9', '4f1765520d6346f9bd9c79e2479e5b12', '研发部', NULL, NULL, NULL,
        NULL, NULL, '1', 0, 'flycloud', now(), 'flycloud', now());

-- 初始化角色
INSERT INTO `flycloud`.`sys_role` (`uuid`, `role_name`, `role_code`, `description`, `create_user`, `create_time`,
                                   `update_user`, `update_time`)
VALUES ('f6817f48af4fb3af11b9e8bf182f618b', '管理员', 'admin', '管理员', 'auto', now(), 'auto',
        now());


-- 初始化用户 ~ 部门关联表
INSERT INTO `flycloud`.`sys_user_dept` (`uuid`, `user_uuid`, `dept_uuid`)
VALUES ('1255ebc5dfc611eaa09238d5477a5fc1', 'e9ca23d68d884d4ebb19d07889727dae',
        '4f1765520d6346f9bd9c79e2479e5b12');

-- 初始化用户 ~ 角色关联表
INSERT INTO `flycloud`.`sys_user_role` (`uuid`, `user_uuid`, `role_uuid`)
VALUES ('42448bdedfc611eaa09238d5477a5fc1', 'e9ca23d68d884d4ebb19d07889727dae',
        'f6817f48af4fb3af11b9e8bf182f618b');


-- 初始化菜单表
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( 'homepage', NULL, '首页', NULL, NULL, 'home', 'dashboard/Analysis', NULL, '/dashboard/analysis', NULL, 0.00, 0, b'1', b'1', NULL, NULL, 0, b'1', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( 'd7d6e2e4e2934f2c9385a623fd98c6f3', NULL, '系统管理', NULL, NULL, 'setting', 'layouts/RouteView', NULL, '/system', NULL, 1.00, 0, b'0', b'1', NULL, NULL, 0, b'1', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '717f6bee46f44a3897eca9abd6e2ec44', NULL, '个人页', NULL, NULL, 'user', 'layouts/RouteView', NULL, '/account', NULL, 2.00, 0, b'0', b'1', b'0', NULL, 0, b'0', NULL, b'0', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '2e42e3835c2b44ec9f7bc26c146ee531', NULL, '结果页', NULL, NULL, 'setting', 'layouts/PageView', NULL, '/result', NULL, 3.00, 0, b'0', b'1', NULL, NULL, 0, b'0', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( 'c65321e57b7949b7a975313220de0422', NULL, '异常页', NULL, NULL, 'warning', 'layouts/RouteView', NULL, '/exception', NULL, 4.00, 0, b'0', b'1', NULL, NULL, 0, b'0', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '190c2b43bec6a5f7a4194a85db67d96a', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '角色管理', NULL, NULL, NULL, 'system/RoleUserList', NULL, '/system/role', NULL, 1.20, 1, b'1', b'1', b'0', NULL, 0, b'1', NULL, b'0', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '3f915b2769fc80648e92d04e84ca059d', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '用户管理', NULL, NULL, NULL, 'system/UserList', NULL, '/system/user', NULL, 1.10, 1, b'0', b'1', b'0', NULL, 0, b'1', NULL, b'0', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '45c966826eeff4c99b8f8ebfe74511fc', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '部门管理', NULL, NULL, NULL, 'system/DepartList', NULL, '/system/depart', NULL, 1.30, 1, b'0', b'1', b'0', NULL, 0, b'1', NULL, b'0', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '54dd5457a3190740005c1bfec55b1c34', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '权限管理', NULL, NULL, NULL, 'system/PermissionList', NULL, '/system/permission', NULL, 1.40, 1, b'1', b'1', b'0', NULL, 0, b'1', NULL, b'0', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '6e73eb3c26099c191bf03852ee1310a1', '717f6bee46f44a3897eca9abd6e2ec44', '个人设置', NULL, NULL, NULL, 'account/settings/Index', NULL, '/account/settings/Index', NULL, 2.30, 1, b'0', b'1', NULL, NULL, 0, b'0', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '8fb8172747a78756c11916216b8b8066', '717f6bee46f44a3897eca9abd6e2ec44', '工作台', NULL, NULL, NULL, 'dashboard/Workplace', NULL, '/dashboard/workplace', NULL, 2.10, 1, b'1', b'1', NULL, NULL, 0, b'0', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( 'd86f58e7ab516d3bc6bfb1fe10585f97', '717f6bee46f44a3897eca9abd6e2ec44', '个人中心', NULL, NULL, NULL, 'account/center/Index', NULL, '/account/center', NULL, 2.20, 1, b'1', b'1', NULL, NULL, 0, b'0', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '00a2a0ae65cdca5e93209cdbde97cbe6', '2e42e3835c2b44ec9f7bc26c146ee531', '成功', NULL, NULL, NULL, 'result/Success', NULL, '/result/success', NULL, 3.10, 1, b'1', b'1', NULL, NULL, 0, b'0', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '13212d3416eb690c2e1d5033166ff47a', '2e42e3835c2b44ec9f7bc26c146ee531', '失败', NULL, NULL, NULL, 'result/Error', NULL, '/result/fail', NULL, 3.20, 1, b'1', b'1', NULL, NULL, 0, b'0', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( '65a8f489f25a345836b7f44b1181197a', 'c65321e57b7949b7a975313220de0422', '403', NULL, NULL, NULL, 'exception/403', NULL, '/exception/403', NULL, 4.10, 1, b'1', b'1', NULL, NULL, 0, b'0', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( 'b4dfc7d5dd9e8d5b6dd6d4579b1aa559', 'c65321e57b7949b7a975313220de0422', '500', NULL, NULL, NULL, 'exception/500', NULL, '/exception/500', NULL, 4.20, 1, b'1', b'1', NULL, NULL, 0, b'0', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `flycloud`.`sys_permission`( `uuid`, `parent_id`, `name`, `perms`, `perms_type`, `icon`, `component`, `component_name`, `url`, `redirect`, `sort_no`, `menu_type`, `leaf`, `route`, `keep_alive`, `description`, `rule_flag`, `hidden`, `status`, `internal_or_external`, `create_user`, `create_time`, `update_user`, `update_time`, `deleted`)
VALUES ( 'd2bbf9ebca5a8fa2e227af97d2da7548', 'c65321e57b7949b7a975313220de0422', '404', NULL, NULL, NULL, 'exception/404', NULL, '/exception/404', NULL, 4.30, 1, b'1', b'1', NULL, NULL, 0, b'0', NULL, NULL, 'admin', NOW(), 'admin', NOW(), b'0');

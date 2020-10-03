-- 初始化管理员账户
INSERT INTO `flycloud`.`sys_user`
(`uuid`, `username`, `password`, `realname`, `avatar`, `nickname`, `mail`, `phone`, `create_user`, `create_time`,
 `update_user`, `update_time`, `enable_status`)
VALUES ('e9ca23d68d884d4ebb19d07889727dae', 'flycloud', 'cb362cfeefbf3d8d', '管理员',
        'http://minio.jeecg.com/otatest/temp/lgo33_1583397323099.png',
        '管理员', 'jeecg@163.com', '18611111111', 'A01', now(), 'A01', now(), true);

-- 初始化部门表
INSERT INTO `flycloud`.`sys_dept` (`uuid`, `parent_id`, `depart_name`, `description`,
                                   `mobile`, `fax`, `address`, `memo`, `status`, `del_flag`, `create_by`, `create_time`,
                                   `update_by`, `update_time`)
VALUES ('4f1765520d6346f9bd9c79e2479e5b12', '', '上海飞云科技', NULL, NULL, NULL, NULL, NULL, '1', '0', 'flycloud',
        now(), 'flycloud', now());

INSERT INTO `flycloud`.`sys_dept` (`uuid`, `parent_id`, `depart_name`, `description`,
                                   `mobile`, `fax`, `address`, `memo`, `status`, `del_flag`, `create_by`, `create_time`,
                                   `update_by`, `update_time`)
VALUES ('5159cde220114246b045e574adceafe9', '4f1765520d6346f9bd9c79e2479e5b12', '研发部', NULL, NULL, NULL,
        NULL, NULL, '1', '0', 'flycloud', now(), 'flycloud', now());

-- 初始化角色
INSERT INTO `flycloud`.`sys_role` (`uuid`, `role_name`, `role_code`, `description`, `create_by`, `create_time`,
                                   `update_by`, `update_time`)
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
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('00a2a0ae65cdca5e93209cdbde97cbe6', '2e42e3835c2b44ec9f7bc26c146ee531', '成功', '/result/success',
        'result/Success', NULL, NULL, '1', NULL, NULL, '1.00', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL,
        '2018-12-25 20:34:38', NULL, NULL, '0', '0', NULL, NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('13212d3416eb690c2e1d5033166ff47a', '2e42e3835c2b44ec9f7bc26c146ee531', '失败', '/result/fail', 'result/Error',
        NULL, NULL, '1', NULL, NULL, '2.00', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, '2018-12-25 20:34:38', NULL,
        NULL, '0', '0', NULL, NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('190c2b43bec6a5f7a4194a85db67d96a', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '角色管理', '/isystem/roleUserList',
        'system/RoleUserList', NULL, NULL, '1', NULL, NULL, '1.20', 0, NULL, 1, 1, 0, 0, NULL, 'admin',
        '2019-04-17 15:13:56', 'admin', '2019-12-25 09:36:31', '0', '0', NULL, 0);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('2e42e3835c2b44ec9f7bc26c146ee531', '', '结果页', '/result', 'layouts/PageView', NULL, NULL, '0', NULL, NULL,
        '8.00', 0, 'setting', 1, 0, NULL, 0, NULL, NULL, '2018-12-25 20:34:38', 'admin', '2019-04-02 11:46:56',
        '0', '0', NULL, NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('3f915b2769fc80648e92d04e84ca059d', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '用户管理', '/isystem/user',
        'system/UserList', NULL, NULL, '1', NULL, NULL, '1.10', 0, NULL, 1, 0, 0, 0, NULL, NULL,
        '2018-12-25 20:34:38', 'admin', '2019-12-25 09:36:24', '0', '0', NULL, 0);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('45c966826eeff4c99b8f8ebfe74511fc', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '部门管理', '/isystem/depart',
        'system/DepartList', NULL, NULL, '1', NULL, NULL, '1.40', 0, NULL, 1, 0, 0, 0, NULL, 'admin',
        '2019-01-29 18:47:40', 'admin', '2019-12-25 09:36:47', '0', '0', NULL, 0);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('54dd5457a3190740005c1bfec55b1c34', 'd7d6e2e4e2934f2c9385a623fd98c6f3', '菜单管理', '/isystem/permission',
        'system/PermissionList', NULL, NULL, '1', NULL, NULL, '1.30', 0, NULL, 1, 1, 0, 0, NULL, NULL,
        '2018-12-25 20:34:38', 'admin', '2019-12-25 09:36:39', '0', '0', NULL, 0);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('65a8f489f25a345836b7f44b1181197a', 'c65321e57b7949b7a975313220de0422', '403', '/exception/403',
        'exception/403', NULL, NULL, '1', NULL, NULL, '1.00', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL,
        '2018-12-25 20:34:38', NULL, NULL, '0', '0', NULL, NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('6e73eb3c26099c191bf03852ee1310a1', '717f6bee46f44a3897eca9abd6e2ec44', '个人设置', '/account/settings/Index',
        'account/settings/Index', NULL, NULL, '1', NULL, NULL, '2.00', 1, NULL, 1, 0, NULL, 0, NULL, NULL,
        '2018-12-25 20:34:38', 'admin', '2019-04-19 09:41:05', '0', '0', NULL, NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('717f6bee46f44a3897eca9abd6e2ec44', '', '个人页', '/account', 'layouts/RouteView', NULL, NULL, '0', NULL, NULL,
        '9.00', 0, 'user', 1, 0, 0, 1, NULL, NULL, '2018-12-25 20:34:38', 'admin', '2020-02-23 22:41:37', '0',
        '0', NULL, 0);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('8fb8172747a78756c11916216b8b8066', '717f6bee46f44a3897eca9abd6e2ec44', '工作台', '/dashboard/workplace',
        'dashboard/Workplace', NULL, NULL, '1', NULL, NULL, '3.00', 0, NULL, 1, 1, NULL, 0, NULL, NULL,
        '2018-12-25 20:34:38', 'admin', '2019-04-02 11:45:02', '0', '0', NULL, NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('9502685863ab87f0ad1134142788a385', '', '首页', '/dashboard/analysis', 'dashboard/Analysis', NULL, NULL, '0',
        NULL, NULL, '0.00',0, 'home', 1, 1, NULL, 0, NULL, NULL, '2018-12-25 20:34:38', 'admin',
        '2019-03-29 11:04:13', '0', '0', NULL, NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('b4dfc7d5dd9e8d5b6dd6d4579b1aa559', 'c65321e57b7949b7a975313220de0422', '500', '/exception/500',
        'exception/500', NULL, NULL, '1', NULL, NULL, '3.00', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL,
        '2018-12-25 20:34:38', NULL, NULL, '0', '0', NULL, NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('c65321e57b7949b7a975313220de0422', NULL, '异常页', '/exception', 'layouts/RouteView', NULL, NULL, '0', NULL, NULL,
        '8.00', NULL, 'warning', 1, 0, NULL, NULL, NULL, NULL, '2018-12-25 20:34:38', NULL, NULL, '0', '0', NULL,
        NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('d2bbf9ebca5a8fa2e227af97d2da7548', 'c65321e57b7949b7a975313220de0422', '404', '/exception/404',
        'exception/404', NULL, NULL, '1', NULL, NULL, '2.00', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL,
        '2018-12-25 20:34:38', NULL, NULL, '0', '0', NULL, NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('d7d6e2e4e2934f2c9385a623fd98c6f3', '', '系统管理', '/isystem', 'layouts/RouteView', NULL, NULL, '0', NULL, NULL,
        '4.00', 0, 'setting', 1, 0, NULL, 0, NULL, NULL, '2018-12-25 20:34:38', 'admin', '2019-03-31 22:19:52',
        '0', '0', NULL, NULL);
INSERT INTO `flycloud`.`sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`,
                                         `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`,
                                         `route`, `leaf`, `keep_alive`, `hidden`, `description`, `create_by`,
                                         `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`,
                                         `internal_or_external`)
VALUES ('d86f58e7ab516d3bc6bfb1fe10585f97', '717f6bee46f44a3897eca9abd6e2ec44', '个人中心', '/account/center',
        'account/center/Index', NULL, NULL, '1', NULL, NULL, '1.00', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL,
        '2018-12-25 20:34:38', NULL, NULL, '0', '0', NULL, NULL);



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


-- 初始化管理员账户
INSERT INTO `flycloud`.`sys_user`
(`uuid`, `username`, `password`, `realname`, `avatar`, `nickname`, `mail`, `phone`, `create_user`, `create_time`,
 `update_user`, `update_time`, `enable_status`)
VALUES ('e9ca23d68d884d4ebb19d07889727dae', 'flycloud', 'cb362cfeefbf3d8d', '管理员',
        'http://minio.jeecg.com/otatest/temp/lgo33_1583397323099.png',
        '管理员', 'jeecg@163.com', '18611111111', 'A01', '2018-12-05 00:00:00', 'A01', '2018-12-05 00:00:00', '1');

-- 初始化部门表
INSERT INTO `flycloud`.`sys_dept` (`serial_version_u_i_d`, `id`, `uuid`, `parent_id`, `depart_name`, `description`,
                                   `mobile`, `fax`, `address`, `memo`, `status`, `del_flag`, `create_by`, `create_time`,
                                   `update_by`, `update_time`)
VALUES (NULL, '0', '4f1765520d6346f9bd9c79e2479e5b12', '', '上海飞云科技', NULL, NULL, NULL, NULL, NULL, '1', '0', 'flycloud',
        '2019-02-20 17:15:34', 'flycloud', '2019-02-26 16:36:18');
INSERT INTO `flycloud`.`sys_dept` (`serial_version_u_i_d`, `id`, `uuid`, `parent_id`, `depart_name`, `description`,
                                   `mobile`, `fax`, `address`, `memo`, `status`, `del_flag`, `create_by`, `create_time`,
                                   `update_by`, `update_time`)
VALUES (NULL, '1', '5159cde220114246b045e574adceafe9', '4f1765520d6346f9bd9c79e2479e5b12', '研发部', NULL, NULL, NULL,
        NULL, NULL, '1', '0', 'flycloud', '2019-02-26 16:44:38', 'flycloud', '2019-03-07 09:36:53');

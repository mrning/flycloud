package com.zac.flycloud.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zac.flycloud.entity.tablemodel.SysPermission;
import com.zac.flycloud.mapper.SysPermissionMapper;
import com.zac.flycloud.sys.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Resource
    private SysPermissionMapper sysPermissionMapper;

}

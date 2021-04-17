package com.zac.flycloud.sys.impl;

import com.zac.flycloud.base.SysBaseApiImpl;
import com.zac.flycloud.mapper.SysPermissionMapper;
import com.zac.flycloud.sys.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysPermissionServiceImpl extends SysBaseApiImpl implements SysPermissionService {
    @Resource
    private SysPermissionMapper sysPermissionMapper;

}

package com.zac.flycloud.start.service;

import com.zac.flycloud.start.bean.basebean.PageResult;
import com.zac.flycloud.start.bean.entity.SysUserDept;
import com.zac.flycloud.start.bean.vos.request.UserDeptRequest;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysUserDeptService extends SysBaseService<SysUserDept> {
    Integer add(SysUserDept sysUserDept);

    Integer del(SysUserDept sysUserDept);

    Integer update(SysUserDept sysUserDept);

    PageResult<SysUserDept> queryPage(UserDeptRequest userDeptRequest);
}
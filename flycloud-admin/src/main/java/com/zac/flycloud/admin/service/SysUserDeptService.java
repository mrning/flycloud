package com.zac.flycloud.admin.service;

import com.zac.flycloud.admin.beans.entity.SysUserDept;
import com.zac.flycloud.admin.beans.vos.request.UserDeptRequest;
import com.zac.flycloud.common.basebeans.PageResult;

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
package com.zac.admin.service;

import com.zac.admin.beans.vos.response.SysUserResponse;
import com.zac.admin.beans.vos.request.UserDeptRequest;
import com.zac.base.bizentity.SysUserDept;
import com.zac.base.basebeans.PageResult;

import java.util.List;

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

    Integer delByUserUuid(String userUuid);

    Integer updateByUserUuid(String userUuid, List<String> deptUuids);

    List<SysUserDept> queryDeptsByUserUuid(String userUuid);

    List<SysUserResponse> userListByDept(UserDeptRequest userDeptRequest);

}
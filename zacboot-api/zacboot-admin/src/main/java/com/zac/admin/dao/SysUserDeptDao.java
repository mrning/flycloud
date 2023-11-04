package com.zac.admin.dao;


import cn.hutool.db.Page;
import com.zac.base.bizentity.SysDept;
import com.zac.base.bizentity.SysUser;
import com.zac.admin.beans.vos.request.UserDeptRequest;
import com.zac.base.bizentity.SysUserDept;

import java.util.List;

public interface SysUserDeptDao {

    List<SysDept> getDeptsByUserUuid(String userUuid);

    List<SysUser> getUsersByDeptUuid(String deptUuid);

    Integer add(SysUserDept sysUserDept);

    Integer del(SysUserDept sysUserDept);

    Integer update(SysUserDept sysUserDept);

    List<SysUserDept> queryPage(UserDeptRequest userDeptRequest, Page page);

    Long queryPageCount(UserDeptRequest userDeptRequest);

    Integer delByUserUuid(String userUuid);

    List<SysUserDept> queryUserDepts(String userUuid);
}

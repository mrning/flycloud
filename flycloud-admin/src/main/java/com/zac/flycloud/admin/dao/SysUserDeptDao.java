package com.zac.flycloud.admin.dao;


import cn.hutool.db.Page;
import com.zac.flycloud.admin.beans.entity.SysDept;
import com.zac.flycloud.admin.beans.entity.SysUserDept;
import com.zac.flycloud.admin.beans.vos.request.UserDeptRequest;

import java.util.List;

public interface SysUserDeptDao {

    List<SysDept> getDeptsByUserUuid(String userUuid);

    Integer add(SysUserDept sysUserDept);

    Integer del(SysUserDept sysUserDept);

    Integer update(SysUserDept sysUserDept);

    List<SysUserDept> queryPage(UserDeptRequest userDeptRequest, Page page);

    Long queryPageCount(UserDeptRequest userDeptRequest);
}

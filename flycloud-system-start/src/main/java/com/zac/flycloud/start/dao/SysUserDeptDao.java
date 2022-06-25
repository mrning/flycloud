package com.zac.flycloud.start.dao;


import cn.hutool.db.Page;
import com.zac.flycloud.start.bean.entity.SysDept;
import com.zac.flycloud.start.bean.entity.SysUserDept;
import com.zac.flycloud.start.bean.vos.request.UserDeptRequest;

import java.util.List;

public interface SysUserDeptDao {

    List<SysDept> getDeptsByUserUuid(String userUuid);

    Integer add(SysUserDept sysUserDept);

    Integer del(SysUserDept sysUserDept);

    Integer update(SysUserDept sysUserDept);

    List<SysUserDept> queryPage(UserDeptRequest userDeptRequest, Page page);

    Long queryPageCount(UserDeptRequest userDeptRequest);
}

package com.zac.flycloud.dao;


import cn.hutool.db.Page;
import com.zac.flycloud.bean.dto.SysDept;
import com.zac.flycloud.bean.dto.SysUserDept;
import com.zac.flycloud.bean.vos.request.UserDeptRequest;

import java.util.List;

public interface SysUserDeptDao {

    List<SysDept> getDeptsByUserUuid(String userUuid);

    Integer add(SysUserDept sysUserDept);

    Integer del(SysUserDept sysUserDept);

    Integer update(SysUserDept sysUserDept);

    List<SysUserDept> queryPage(UserDeptRequest userDeptRequest, Page page);

    Long queryPageCount(UserDeptRequest userDeptRequest);
}

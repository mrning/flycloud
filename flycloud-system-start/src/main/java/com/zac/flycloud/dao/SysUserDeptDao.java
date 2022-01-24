package com.zac.flycloud.dao;


import cn.hutool.db.Page;
import com.zac.flycloud.bean.dto.SysDept;
import com.zac.flycloud.bean.dto.SysUserDept;
import com.zac.flycloud.bean.vos.UserDeptRequestVO;

import java.util.List;

public interface SysUserDeptDao {

    List<SysDept> getDeptsByUserUuid(String userUuid);

    Integer add(SysUserDept sysUserDept);

    Integer del(SysUserDept sysUserDept);

    Integer update(SysUserDept sysUserDept);

    List<SysUserDept> queryPage(UserDeptRequestVO userDeptRequestVO, Page page);

    Long queryPageCount(UserDeptRequestVO userDeptRequestVO);
}

package com.zac.flycloud.service;

import com.zac.flycloud.basebean.PageResult;
import com.zac.flycloud.base.SysBaseService;
import com.zac.flycloud.dto.SysUserDeptDTO;
import com.zac.flycloud.tablemodel.SysUserDept;
import com.zac.flycloud.vos.UserDeptRequestVO;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysUserDeptService extends SysBaseService<SysUserDept> {
    Integer add(SysUserDeptDTO sysUserDeptDTO);

    Integer del(SysUserDeptDTO sysUserDeptDTO);

    Integer update(SysUserDeptDTO sysUserDeptDTO);

    PageResult<SysUserDeptDTO> queryPage(UserDeptRequestVO userDeptRequestVO);
}
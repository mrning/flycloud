package com.zac.flycloud.service;

import com.zac.flycloud.bean.basebean.PageResult;
import com.zac.flycloud.service.SysBaseService;
import com.zac.flycloud.bean.dto.SysUserDeptDTO;
import com.zac.flycloud.bean.tablemodel.SysUserDept;
import com.zac.flycloud.bean.vos.UserDeptRequestVO;

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
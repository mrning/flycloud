package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.dto.SysUserDeptDTO;
import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysUserDeptDao {
    Integer add(SysUserDeptDTO sysUserDeptDTO);

    Integer del(SysUserDeptDTO sysUserDeptDTO);

    Integer update(SysUserDeptDTO sysUserDeptDTO);

    List<SysUserDeptDTO> queryPage(SysUserDeptDTO sysUserDeptDTO, Page page);

    Long queryPageCount(SysUserDeptDTO sysUserDeptDTO);
}
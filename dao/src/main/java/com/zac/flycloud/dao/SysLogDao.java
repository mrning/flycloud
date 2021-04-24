package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.dto.SysLogDTO;
import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
public interface SysLogDao {
    Integer add(SysLogDTO sysLogDTO);

    Integer del(SysLogDTO sysLogDTO);

    Integer update(SysLogDTO sysLogDTO);

    List<SysLogDTO> queryPage(SysLogDTO sysLogDTO, Page page);

    Long queryPageCount(SysLogDTO sysLogDTO);
}
package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.bean.dto.SysLogDTO;
import com.zac.flycloud.bean.vos.SysLogRequestVO;

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

    List<SysLogDTO> queryPage(SysLogRequestVO sysLogRequestVO, Page page);

    Long queryPageCount(SysLogRequestVO sysLogRequestVO);
}
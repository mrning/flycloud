package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.bean.dto.SysLog;
import com.zac.flycloud.bean.vos.SysLogRequestVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * AutoCreateFile
 *
 * @author zac
 * @date 2021年4月24日星期六
 */
public interface SysLogDao {
    Integer add(SysLog sysLog);

    Integer del(SysLog sysLog);

    Integer update(SysLog sysLog);

    List<SysLog> queryPage(SysLogRequestVO sysLogRequestVO, Page page);

    Long queryPageCount(SysLogRequestVO sysLogRequestVO);

    /**
     * @功能：清空所有日志记录
     */
    void removeAll();

    Long findTotalVisitCount();

    Long findTodayVisitCount(Date dayStart, Date dayEnd);

    Long findTodayIp(Date dayStart, Date dayEnd);

    List<Map<String, Object>> findVisitCount(Date dayStart, Date dayEnd);
}
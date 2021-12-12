package com.zac.flycloud.service;


import com.zac.flycloud.bean.basebean.PageResult;
import com.zac.flycloud.bean.dto.SysLogDTO;
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
public interface SysLogService {
    Integer add(SysLogDTO sysLogDTO);

    Integer del(SysLogDTO sysLogDTO);

    Integer update(SysLogDTO sysLogDTO);

    PageResult<SysLogDTO> queryPage(SysLogRequestVO sysLogRequestVO);

    /**
     * @功能：清空所有日志记录
     */
    void removeAll();

    /**
     * 获取系统总访问次数
     *
     * @return Long
     */
    Long findTotalVisitCount();

    /**
     * 获取系统今日访问次数
     *
     * @return Long
     */
    Long findTodayVisitCount(Date dayStart, Date dayEnd);

    /**
     * 获取系统今日访问 IP数
     *
     * @return Long
     */
    Long findTodayIp(Date dayStart, Date dayEnd);

    /**
     * 首页：根据时间统计访问数量/ip数量
     *
     * @param dayStart
     * @param dayEnd
     * @return
     */
    List<Map<String, Object>> findVisitCount(Date dayStart, Date dayEnd);
}
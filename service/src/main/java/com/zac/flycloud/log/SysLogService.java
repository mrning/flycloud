package com.zac.flycloud.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zac.flycloud.tablemodel.SysLog;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @Author zac
 * @since 2018-12-26
 */
public interface SysLogService extends IService<SysLog> {

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
	 *   首页：根据时间统计访问数量/ip数量
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	List<Map<String,Object>> findVisitCount(Date dayStart, Date dayEnd);
}

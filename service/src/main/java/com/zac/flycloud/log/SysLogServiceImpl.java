package com.zac.flycloud.log;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zac.flycloud.base.SysBaseAPI;
import com.zac.flycloud.mapper.SysLogMapper;
import com.zac.flycloud.tablemodel.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @Author zac
 * @since 2018-12-26
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

	@Resource
	private SysLogMapper sysLogMapper;
	@Autowired
	private SysBaseAPI sysBaseAPI;
	
	/**
	 * @功能：清空所有日志记录
	 */
	@Override
	public void removeAll() {
		sysLogMapper.removeAll();
	}

	@Override
	public Long findTotalVisitCount() {
		return sysLogMapper.findTotalVisitCount();
	}

	@Override
	public Long findTodayVisitCount(Date dayStart, Date dayEnd) {
		return sysLogMapper.findTodayVisitCount(dayStart,dayEnd);
	}

	@Override
	public Long findTodayIp(Date dayStart, Date dayEnd) {
		return sysLogMapper.findTodayIp(dayStart,dayEnd);
	}

	@Override
	public List<Map<String,Object>> findVisitCount(Date dayStart, Date dayEnd) {
			return sysLogMapper.findVisitCount(dayStart, dayEnd,"MYSQL");
	}
}

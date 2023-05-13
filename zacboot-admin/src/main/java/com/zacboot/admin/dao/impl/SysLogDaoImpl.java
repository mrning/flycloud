package com.zacboot.admin.dao.impl;

import com.zacboot.system.core.entity.admin.SysLog;
import com.zacboot.admin.beans.example.SysLogExample;
import com.zacboot.admin.beans.vos.request.SysLogRequest;
import com.zacboot.admin.dao.SysLogDao;
import com.zacboot.admin.mapper.SysLogMapper;
import com.zacboot.system.core.util.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
@Repository
@Slf4j
public class SysLogDaoImpl implements SysLogDao {
    @Autowired
    private SysLogMapper sysLogMapper;

    public Integer add(SysLog sysLog) {
        sysLog.setCreateTime(LocalDateTime.now());
        sysLog.setCreateUser(SysUtil.getCurrentUser().getNickname());
        sysLog.setDeleted(false);
        return sysLogMapper.insertSelective(sysLog);
    }

    public Integer del(SysLog sysLog) {
        SysLogExample sysLogExample = new SysLogExample();
        return sysLogMapper.deleteByExample(sysLogExample);
    }

    public Integer update(SysLog sysLog) {
        SysLogExample sysLogExample = new SysLogExample();
        return sysLogMapper.updateByExampleSelective(sysLog, sysLogExample);
    }

    public List<SysLog> queryPage(SysLogRequest sysLogRequest, cn.hutool.db.Page page) {
        SysLogExample sysLogExample = new SysLogExample();
        return sysLogMapper.selectByExampleWithRowbounds(sysLogExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(SysLogRequest sysLogRequest) {
        SysLogExample sysLogExample = new SysLogExample();
        return sysLogMapper.countByExample(sysLogExample);
    }

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
        return sysLogMapper.findTodayVisitCount(dayStart, dayEnd);
    }

    @Override
    public Long findTodayIp(Date dayStart, Date dayEnd) {
        return sysLogMapper.findTodayIp(dayStart, dayEnd);
    }

    @Override
    public List<Map<String, Object>> findVisitCount(Date dayStart, Date dayEnd) {
        return sysLogMapper.findVisitCount(dayStart, dayEnd, "MYSQL");
    }
}
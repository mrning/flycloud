package com.zac.flycloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.zac.flycloud.bean.basebean.PageResult;
import com.zac.flycloud.bean.dto.SysLog;
import com.zac.flycloud.bean.vos.SysLogRequestVO;
import com.zac.flycloud.dao.SysLogDao;
import com.zac.flycloud.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * AutoCreateFile
 *
 * @author zac
 * @date 2021年4月24日星期六
 */
@Slf4j
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    public Integer add(SysLog SysLog) {
        return sysLogDao.add(SysLog);
    }

    public Integer del(SysLog SysLog) {
        Assert.isTrue(BeanUtil.isEmpty(SysLog), "不能全部属性为空，会删除全表数据");
        return sysLogDao.del(SysLog);
    }

    public Integer update(SysLog SysLog) {
        return sysLogDao.update(SysLog);
    }

    public PageResult<SysLog> queryPage(SysLogRequestVO sysLogRequestVO) {
        PageResult<SysLog> pageResult = new PageResult<>();
        pageResult.setDataList(sysLogDao.queryPage(sysLogRequestVO, new Page(sysLogRequestVO.getPageNumber(), sysLogRequestVO.getPageSize())));
        pageResult.setTotal(sysLogDao.queryPageCount(sysLogRequestVO).intValue());
        return pageResult;
    }

    @Override
    public void removeAll() {
        sysLogDao.removeAll();
    }

    @Override
    public Long findTotalVisitCount() {
        return sysLogDao.findTotalVisitCount();
    }

    @Override
    public Long findTodayVisitCount(Date dayStart, Date dayEnd) {
        return sysLogDao.findTodayVisitCount(dayStart,dayEnd);
    }

    @Override
    public Long findTodayIp(Date dayStart, Date dayEnd) {
        return sysLogDao.findTodayIp(dayStart,dayEnd);
    }

    @Override
    public List<Map<String, Object>> findVisitCount(Date dayStart, Date dayEnd) {
        return sysLogDao.findVisitCount(dayStart,dayEnd);
    }
}
package com.zac.flycloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import com.zac.flycloud.dao.SysLogDao;
import com.zac.flycloud.dto.SysLogDTO;
import com.zac.flycloud.mapper.SysLogDTOMapper;
import com.zac.flycloud.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
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

    @Resource
    private SysLogDTOMapper sysLogMapper;

    public Integer add(SysLogDTO sysLogDTO) {
        return sysLogDao.add(sysLogDTO);
    }

    public Integer del(SysLogDTO sysLogDTO) {
        Assert.isTrue(BeanUtil.isEmpty(sysLogDTO), "不能全部属性为空，会删除全表数据");
        return sysLogDao.del(sysLogDTO);
    }

    public Integer update(SysLogDTO sysLogDTO) {
        return sysLogDao.update(sysLogDTO);
    }

    public PageResult<SysLogDTO> queryPage(SysLogDTO sysLogDTO) {
        PageResult<SysLogDTO> pageResult = new PageResult<>();
        pageResult.addAll(sysLogDao.queryPage(sysLogDTO, new Page(sysLogDTO.getPageNumber(), sysLogDTO.getPageSize())));
        pageResult.setTotal(sysLogDao.queryPageCount(sysLogDTO).intValue());
        return pageResult;
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
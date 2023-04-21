package com.zacboot.admin.work.dao.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.db.Page;

import java.time.LocalDateTime;
import java.util.List;

import com.zacboot.admin.work.beans.example.AppThreeReportExample;
import com.zacboot.admin.work.dao.AppThreeReportDao;
import com.zacboot.admin.work.mapper.AppThreeReportMapper;
import com.zacboot.system.core.util.SysUtil;
import com.zacboot.system.core.entity.admin.AppThreeReport;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 * @date 2023年4月20日星期四
 * @author zac
 */
@Repository
@Slf4j
public class AppThreeReportDaoImpl implements AppThreeReportDao {
    @Autowired
    private AppThreeReportMapper appThreeReportMapper;

    public Integer add(AppThreeReport appThreeReport) {
        appThreeReport.setUuid(UUID.randomUUID().toString(true));
        appThreeReport.setCreateTime(LocalDateTime.now());
        appThreeReport.setCreateUser(SysUtil.getCurrentUser().getNickname());
        appThreeReport.setDeleted(false);
        return appThreeReportMapper.insertSelective(appThreeReport);
    }

    public Integer del(AppThreeReport appThreeReport) {
        AppThreeReportExample appThreeReportExample = new AppThreeReportExample();
        buildExample(appThreeReport,appThreeReportExample);
        return appThreeReportMapper.deleteByExample(appThreeReportExample);
    }

    public Integer update(AppThreeReport appThreeReport) {
        AppThreeReportExample appThreeReportExample = new AppThreeReportExample();
        buildExample(appThreeReport,appThreeReportExample);
        return appThreeReportMapper.updateByExampleSelective(appThreeReport,appThreeReportExample);
    }

    public List<AppThreeReport> queryPage(AppThreeReport threeReport,Page page) {
        AppThreeReportExample appThreeReportExample = new AppThreeReportExample();
        buildExample(threeReport,appThreeReportExample);
        return appThreeReportMapper.selectByExampleWithRowbounds(appThreeReportExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(AppThreeReport appThreeReport) {
        AppThreeReportExample appThreeReportExample = new AppThreeReportExample();
        buildExample(appThreeReport,appThreeReportExample);
        return appThreeReportMapper.countByExample(appThreeReportExample);
    }

    public AppThreeReportExample buildExample(AppThreeReport threeReport, AppThreeReportExample appThreeReportExample) {
        AppThreeReportExample.Criteria criteria = appThreeReportExample.createCriteria();
        criteria.andUserNameLike("%" + threeReport.getUserName()+ "%");
        criteria.andDeptUuidEqualTo(threeReport.getDeptUuid());
        criteria.andRoleUuidEqualTo(threeReport.getRoleUuid());
        criteria.andReportTypeEqualTo(threeReport.getReportType());
        return appThreeReportExample;
    }
}
package com.zacboot.admin.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.admin.work.beans.request.ThreeReportPageRequest;
import com.zacboot.admin.work.dao.AppThreeReportDao;
import com.zacboot.admin.work.mapper.AppThreeReportMapper;
import com.zacboot.admin.work.service.AppThreeReportService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.assess.AppThreeReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * AutoCreateFile
 * @date 2023年4月20日星期四
 * @author zac
 */
@Slf4j
@Service
public class AppThreeReportServiceImpl extends SysBaseServiceImpl<AppThreeReportMapper, AppThreeReport> implements AppThreeReportService {
    @Autowired
    private AppThreeReportDao appThreeReportDao;

    public Integer add(AppThreeReport appThreeReport) {
        return appThreeReportDao.add(appThreeReport);
    }

    public Integer del(AppThreeReport appThreeReport) {
        Assert.isTrue(BeanUtil.isNotEmpty(appThreeReport),"不能全部属性为空，会删除全表数据");
        return appThreeReportDao.del(appThreeReport);
    }

    public Integer update(AppThreeReport appThreeReport) {
        return appThreeReportDao.update(appThreeReport);
    }

    public PageResult<AppThreeReport> queryPage(ThreeReportPageRequest pageRequest) {
        PageResult<AppThreeReport> pageResult = new PageResult<>();
        AppThreeReport appThreeReport = pageRequest.converToDo();
        Page<AppThreeReport> threeReportPage = appThreeReportDao.queryPage(appThreeReport,pageRequest.getPage());
        pageResult.setDataList(threeReportPage.getRecords());
        pageResult.setTotal(threeReportPage.getTotal());
        return pageResult;
    }
}
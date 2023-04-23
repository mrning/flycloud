package com.zacboot.admin.work.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.assess.AppThreeReport;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月20日星期四
 * @author zac
 */
public interface AppThreeReportDao {
    Integer add(AppThreeReport appThreeReport);

    Integer del(AppThreeReport appThreeReport);

    Integer update(AppThreeReport appThreeReport);

    Page<AppThreeReport> queryPage(AppThreeReport threeReport, Page<AppThreeReport> page);

    Long queryPageCount(AppThreeReport threeReport);
}
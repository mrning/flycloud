package com.zacboot.admin.work.dao;

import cn.hutool.db.Page;
import com.zacboot.system.core.entity.admin.AppThreeReport;

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

    List<AppThreeReport> queryPage(AppThreeReport threeReport,Page page);

    Long queryPageCount(AppThreeReport threeReport);
}
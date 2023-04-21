package com.zacboot.admin.work.service;

import com.zacboot.admin.work.beans.request.ThreeReportPageRequest;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.admin.AppThreeReport;

/**
 * AutoCreateFile
 * @date 2023年4月20日星期四
 * @author zac
 */
public interface AppThreeReportService extends SysBaseService<AppThreeReport> {
    Integer add(AppThreeReport appThreeReport);

    Integer del(AppThreeReport appThreeReport);

    Integer update(AppThreeReport appThreeReport);

    PageResult<AppThreeReport> queryPage(ThreeReportPageRequest pageRequest);
}
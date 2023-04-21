package com.zacboot.admin.work.service;


import cn.hutool.db.Page;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.admin.AppAssessAppeal;

/**
 * AutoCreateFile
 * @date 2023年4月20日星期四
 * @author zac
 */
public interface AppAssessAppealService extends SysBaseService<AppAssessAppeal> {
    Integer add(AppAssessAppeal appAssessAppeal);

    Integer del(AppAssessAppeal appAssessAppeal);

    Integer update(AppAssessAppeal appAssessAppeal);

    PageResult<AppAssessAppeal> queryPage(AppAssessAppeal appAssessAppeal, Page page);
}
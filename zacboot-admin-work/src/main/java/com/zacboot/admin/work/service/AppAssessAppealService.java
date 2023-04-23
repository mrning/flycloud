package com.zacboot.admin.work.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.admin.work.beans.request.AppAssessAppealPageRequest;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.assess.AppAssessAppeal;

/**
 * AutoCreateFile
 * @date 2023年4月20日星期四
 * @author zac
 */
public interface AppAssessAppealService extends SysBaseService<AppAssessAppeal> {
    Integer add(AppAssessAppeal appAssessAppeal);

    Integer del(AppAssessAppeal appAssessAppeal);

    Integer update(AppAssessAppeal appAssessAppeal);

    PageResult<AppAssessAppeal> queryPage(AppAssessAppealPageRequest pageRequest);
}
package com.zacboot.admin.work.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.assess.AppAssessAppeal;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月20日星期四
 * @author zac
 */
public interface AppAssessAppealDao {
    Integer add(AppAssessAppeal appAssessAppeal);

    Integer del(AppAssessAppeal appAssessAppeal);

    Integer update(AppAssessAppeal appAssessAppeal);

    Page<AppAssessAppeal> queryPage(AppAssessAppeal appAssessAppeal, Page<AppAssessAppeal> page);

    Long queryPageCount(AppAssessAppeal appAssessAppeal);
}
package com.zacboot.admin.work.dao;

import cn.hutool.db.Page;
import com.zacboot.system.core.entity.admin.AppAssessAppeal;

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

    List<AppAssessAppeal> queryPage(AppAssessAppeal appAssessAppeal, Page page);

    Long queryPageCount(AppAssessAppeal appAssessAppeal);
}
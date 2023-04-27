package com.zacboot.admin.property.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.administration.AppProperty;

/**
 * AutoCreateFile
 * @date 2023年4月26日星期三
 * @author zac
 */
public interface AppPropertyDao {
    Integer add(AppProperty appProperty);

    Integer del(AppProperty appProperty);

    Integer update(AppProperty appProperty);

    Page<AppProperty> queryPage(AppProperty appProperty, Page<AppProperty> page);

    Long queryPageCount(AppProperty appProperty);
}
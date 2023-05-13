package com.zacboot.admin.property.service;

import com.zacboot.admin.property.dto.request.AppPropertyPageRequest;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.administration.AppProperty;

/**
 * AutoCreateFile
 * @date 2023年4月26日星期三
 * @author zac
 */
public interface AppPropertyService extends SysBaseService<AppProperty> {
    Integer add(AppProperty appProperty);

    Integer del(AppProperty appProperty);

    Integer update(AppProperty appProperty);

    PageResult<AppProperty> queryPage(AppPropertyPageRequest pageRequest);
}
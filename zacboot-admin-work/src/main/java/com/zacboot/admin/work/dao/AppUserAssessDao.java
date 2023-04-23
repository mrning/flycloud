package com.zacboot.admin.work.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.assess.AppUserAssess;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
public interface AppUserAssessDao {
    Integer add(AppUserAssess appUserAssess);

    Integer del(AppUserAssess appUserAssess);

    Integer update(AppUserAssess appUserAssess);

    Page<AppUserAssess> queryPage(AppUserAssess appUserAssess, Page<AppUserAssess> page);

    Long queryPageCount(AppUserAssess appUserAssess);

    AppUserAssess queryByUserUuid(String userUuid);
}
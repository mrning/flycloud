package com.zacboot.admin.work.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.assess.AppUserMonthAssess;
import com.zacboot.system.core.entity.assess.AppUserTimeAssess;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
public interface AppUserTimeAssessDao {
    Integer add(AppUserTimeAssess appUserTimeAssess);

    Integer del(AppUserTimeAssess appUserTimeAssess);

    Integer update(AppUserTimeAssess appUserTimeAssess);

    Page<AppUserTimeAssess> queryPage(AppUserTimeAssess appUserTimeAssess, Page<AppUserTimeAssess> page);

    Long queryPageCount(AppUserTimeAssess appUserTimeAssess);

    List<AppUserTimeAssess> queryByUserUuid(String userUuid);
}
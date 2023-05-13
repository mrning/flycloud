package com.zacboot.admin.work.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.assess.AppUserMonthAssess;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
public interface AppUserMonthAssessDao {
    Integer add(AppUserMonthAssess appUserMonthAssess);

    Integer del(AppUserMonthAssess appUserMonthAssess);

    Integer update(AppUserMonthAssess appUserMonthAssess);

    Page<AppUserMonthAssess> queryPage(AppUserMonthAssess appUserMonthAssess, Page<AppUserMonthAssess> page);

    Long queryPageCount(AppUserMonthAssess appUserMonthAssess);

    List<AppUserMonthAssess> queryByUserUuid(String userUuid);
}
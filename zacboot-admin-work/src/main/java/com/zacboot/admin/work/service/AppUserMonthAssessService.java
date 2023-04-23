package com.zacboot.admin.work.service;


import com.zacboot.admin.work.beans.request.AppUserMonthAssessPageRequest;
import com.zacboot.admin.work.beans.response.UserMonthAssessInfo;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.assess.AppUserMonthAssess;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
public interface AppUserMonthAssessService extends SysBaseService<AppUserMonthAssess> {

    Integer add(AppUserMonthAssess appUserMonthAssess);

    Integer del(AppUserMonthAssess appUserMonthAssess);

    Integer update(AppUserMonthAssess appUserMonthAssess);

    PageResult<AppUserMonthAssess> queryPage(AppUserMonthAssessPageRequest pageRequest);

    UserMonthAssessInfo getUserMonthAssessInfo();
}
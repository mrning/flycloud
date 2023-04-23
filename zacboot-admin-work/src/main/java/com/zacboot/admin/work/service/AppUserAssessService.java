package com.zacboot.admin.work.service;


import com.zacboot.admin.work.beans.request.AppUserAssessPageRequest;
import com.zacboot.admin.work.beans.response.UserAssessInfo;
import com.zacboot.admin.work.beans.response.UserMonthAssessInfo;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.assess.AppUserAssess;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
public interface AppUserAssessService extends SysBaseService<AppUserAssess> {
    Integer add(AppUserAssess appUserAssess);

    Integer del(AppUserAssess appUserAssess);

    Integer update(AppUserAssess appUserAssess);

    PageResult<AppUserAssess> queryPage(AppUserAssessPageRequest pageRequest);

    UserAssessInfo getUserAssessInfo();

}
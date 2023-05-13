package com.zacboot.admin.work.service;


import com.zacboot.admin.work.beans.request.AppUserTimeAssessPageRequest;
import com.zacboot.admin.work.beans.response.UserTimeAssessInfo;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.assess.AppUserTimeAssess;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
public interface AppUserTimeAssessService extends SysBaseService<AppUserTimeAssess> {
    Integer add(AppUserTimeAssess appUserTimeAssess);

    Integer del(AppUserTimeAssess appUserTimeAssess);

    Integer update(AppUserTimeAssess appUserTimeAssess);

    PageResult<AppUserTimeAssess> queryPage(AppUserTimeAssessPageRequest pageRequest);

    UserTimeAssessInfo getUserTimeAssessInfo();
}
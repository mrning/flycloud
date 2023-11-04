package com.zac.admin.service;


import com.zac.admin.beans.vos.request.SysActivityPageRequest;
import com.zac.base.basebeans.PageResult;
import com.zac.base.bizentity.SysActivity;

/**
 * AutoCreateFile
 * @date 2023年10月30日星期一
 * @author zac
 */
public interface SysActivityService extends SysBaseService<SysActivity> {
    Integer add(SysActivity sysActivity);

    Integer del(SysActivity sysActivity);

    Integer update(SysActivity sysActivity);

    PageResult<SysActivity> queryPage(SysActivityPageRequest pageRequest);
}
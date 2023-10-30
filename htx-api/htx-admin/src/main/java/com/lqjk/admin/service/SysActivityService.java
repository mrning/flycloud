package com.lqjk.admin.service;


import com.lqjk.admin.beans.vos.request.SysActivityPageRequest;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.base.bizentity.SysActivity;

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
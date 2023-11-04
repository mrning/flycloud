package com.zac.admin.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zac.admin.beans.vos.request.SysActivityPageRequest;
import com.zac.base.bizentity.SysActivity;

/**
 * AutoCreateFile
 * @date 2023年10月30日星期一
 * @author zac
 */
public interface SysActivityDao {
    Integer add(SysActivity sysActivity);

    Integer del(SysActivity sysActivity);

    Integer update(SysActivity sysActivity);

    Page<SysActivity> queryPage(SysActivityPageRequest pageRequest);

    Long queryPageCount(SysActivity sysActivity);
}
package com.zacboot.admin.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.admin.work.beans.request.AppUserAssessPageRequest;
import com.zacboot.admin.work.beans.response.UserAssessInfo;
import com.zacboot.admin.work.beans.response.UserMonthAssessInfo;
import com.zacboot.admin.work.dao.AppUserAssessDao;
import com.zacboot.admin.work.mapper.AppUserAssessMapper;
import com.zacboot.admin.work.service.AppUserAssessService;
import com.zacboot.admin.work.service.AppUserMonthAssessService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.assess.AppUserAssess;
import com.zacboot.system.core.util.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
@Slf4j
@Service
public class AppUserAssessServiceImpl extends SysBaseServiceImpl<AppUserAssessMapper, AppUserAssess> implements AppUserAssessService {
    @Autowired
    private AppUserAssessDao appUserAssessDao;

    @Autowired
    private AppUserMonthAssessService userMonthAssessService;

    public Integer add(AppUserAssess appUserAssess) {
        return appUserAssessDao.add(appUserAssess);
    }

    public Integer del(AppUserAssess appUserAssess) {
        Assert.isTrue(BeanUtil.isNotEmpty(appUserAssess),"不能全部属性为空，会删除全表数据");
        return appUserAssessDao.del(appUserAssess);
    }

    public Integer update(AppUserAssess appUserAssess) {
        return appUserAssessDao.update(appUserAssess);
    }

    public PageResult<AppUserAssess> queryPage(AppUserAssessPageRequest pageRequest) {
        PageResult<AppUserAssess> pageResult = new PageResult<>();
        AppUserAssess appUserAssess = pageRequest.converToDo();
        Page<AppUserAssess> page = appUserAssessDao.queryPage(appUserAssess, pageRequest.getPage());
        pageResult.setDataList(page.getRecords());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    /**
     * 获取当前登录用户考核信息
     * @return
     */
    @Override
    public UserAssessInfo getUserAssessInfo() {
        UserAssessInfo userAssessInfo = new UserAssessInfo();
        AppUserAssess appUserAssess = appUserAssessDao.queryByUserUuid(SysUtil.getCurrentUser().getUuid());
        return userAssessInfo.converByDo(appUserAssess);
    }

}
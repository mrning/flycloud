package com.zacboot.admin.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.admin.work.beans.request.AppUserTimeAssessPageRequest;
import com.zacboot.admin.work.beans.response.UserTimeAssessInfo;
import com.zacboot.admin.work.dao.AppUserTimeAssessDao;
import com.zacboot.admin.work.mapper.AppUserTimeAssessMapper;
import com.zacboot.admin.work.service.AppUserTimeAssessService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.assess.AppUserTimeAssess;
import com.zacboot.system.core.util.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
@Slf4j
@Service
public class AppUserTimeAssessServiceImpl extends SysBaseServiceImpl<AppUserTimeAssessMapper, AppUserTimeAssess> implements AppUserTimeAssessService {
    @Autowired
    private AppUserTimeAssessDao appUserTimeAssessDao;

    @Override
    public Integer add(AppUserTimeAssess appUserTimeAssess) {
        return appUserTimeAssessDao.add(appUserTimeAssess);
    }

    public Integer del(AppUserTimeAssess appUserTimeAssess) {
        Assert.isTrue(BeanUtil.isNotEmpty(appUserTimeAssess),"不能全部属性为空，会删除全表数据");
        return appUserTimeAssessDao.del(appUserTimeAssess);
    }

    public Integer update(AppUserTimeAssess appUserTimeAssess) {
        return appUserTimeAssessDao.update(appUserTimeAssess);
    }

    public PageResult<AppUserTimeAssess> queryPage(AppUserTimeAssessPageRequest pageRequest) {
        PageResult<AppUserTimeAssess> pageResult = new PageResult<>();
        AppUserTimeAssess appUserTimeAssess = pageRequest.converToDo();
        Page<AppUserTimeAssess> page =  appUserTimeAssessDao.queryPage(appUserTimeAssess, new Page<>(pageRequest.getPage(),pageRequest.getLimit()));
        pageResult.setDataList(page.getRecords());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Override
    public UserTimeAssessInfo getUserTimeAssessInfo() {
        UserTimeAssessInfo userTimeAssessInfo = new UserTimeAssessInfo();
        List<AppUserTimeAssess> userTimeAssess =  appUserTimeAssessDao.queryByUserUuid(SysUtil.getCurrentUser().getUuid());
        userTimeAssessInfo.setUserTimeAssessList(userTimeAssess);
        // TODO 是否全勤
        userTimeAssessInfo.setFullAttendance(true);
        return userTimeAssessInfo;
    }
}
package com.zacboot.admin.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.admin.work.beans.request.AppUserMonthAssessPageRequest;
import com.zacboot.admin.work.beans.response.UserAssessInfo;
import com.zacboot.admin.work.beans.response.UserMonthAssessInfo;
import com.zacboot.admin.work.dao.AppUserMonthAssessDao;
import com.zacboot.admin.work.mapper.AppUserMonthAssessMapper;
import com.zacboot.admin.work.service.AppUserMonthAssessService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.assess.AppUserMonthAssess;
import com.zacboot.system.core.util.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
@Slf4j
@Service
public class AppUserMonthAssessServiceImpl extends SysBaseServiceImpl<AppUserMonthAssessMapper, AppUserMonthAssess> implements AppUserMonthAssessService {
    @Autowired
    private AppUserMonthAssessDao appUserMonthAssessDao;

    public Integer add(AppUserMonthAssess appUserMonthAssess) {
        return appUserMonthAssessDao.add(appUserMonthAssess);
    }

    public Integer del(AppUserMonthAssess appUserMonthAssess) {
        Assert.isTrue(BeanUtil.isNotEmpty(appUserMonthAssess),"不能全部属性为空，会删除全表数据");
        return appUserMonthAssessDao.del(appUserMonthAssess);
    }

    public Integer update(AppUserMonthAssess appUserMonthAssess) {
        return appUserMonthAssessDao.update(appUserMonthAssess);
    }

    public PageResult<AppUserMonthAssess> queryPage(AppUserMonthAssessPageRequest pageRequest) {
        PageResult<AppUserMonthAssess> pageResult = new PageResult<>();
        AppUserMonthAssess appUserMonthAssess = pageRequest.converToDo();
        Page<AppUserMonthAssess> page = appUserMonthAssessDao.queryPage(appUserMonthAssess,new Page<>(pageRequest.getPage(),pageRequest.getLimit()));
        pageResult.setDataList(page.getRecords());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Override
    public UserMonthAssessInfo getUserMonthAssessInfo() {
        UserMonthAssessInfo userMonthAssessInfo = new UserMonthAssessInfo();
        List<AppUserMonthAssess> userMonthAssess = appUserMonthAssessDao.queryByUserUuid(SysUtil.getCurrentUser().getUuid());
        userMonthAssessInfo.setUserMonthAssessInfoList(userMonthAssess);
        // 奖励项 TODO
        List<String> reward = new ArrayList<>();
        reward.add("全勤 +1");
        reward.add("三报 +2");
        reward.add("工单完成 +3");
        reward.add("制单达标 +3");
        userMonthAssessInfo.setRewardList(reward);
        // 扣分项 TODO
        List<String> punishment = new ArrayList<>();
        punishment.add("未日毕 -3");
        punishment.add("报错单 -2");
        userMonthAssessInfo.setPunishment(punishment);
        // 考核结果 TODO
        userMonthAssessInfo.setTotalScore(90);
        userMonthAssessInfo.setAssessLevel("A");
        return userMonthAssessInfo;
    }
}
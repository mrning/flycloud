package com.zacboot.admin.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.zacboot.admin.work.dao.AppAssessAppealDao;
import com.zacboot.admin.work.mapper.AppAssessAppealMapper;
import com.zacboot.admin.work.service.AppAssessAppealService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.admin.AppAssessAppeal;
import com.zacboot.system.core.util.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * AutoCreateFile
 * @date 2023年4月20日星期四
 * @author zac
 */
@Slf4j
@Service
public class AppAssessAppealServiceImpl extends SysBaseServiceImpl<AppAssessAppealMapper, AppAssessAppeal> implements AppAssessAppealService {
    @Autowired
    private AppAssessAppealDao appAssessAppealDao;

    public Integer add(AppAssessAppeal appAssessAppeal) {
        return appAssessAppealDao.add(appAssessAppeal);
    }

    public Integer del(AppAssessAppeal appAssessAppeal) {
        Assert.isTrue(BeanUtil.isNotEmpty(appAssessAppeal),"不能全部属性为空，会删除全表数据");
        return appAssessAppealDao.del(appAssessAppeal);
    }

    public Integer update(AppAssessAppeal appAssessAppeal) {
        Assert.notNull(appAssessAppeal.getUuid(),"uuid不能为空");
        if (StringUtils.isBlank(appAssessAppeal.getReceiverName()) || StringUtils.isBlank(appAssessAppeal.getReceiverUuid())){
            appAssessAppeal.setReceiverUuid(SysUtil.getCurrentUser().getUuid());
            appAssessAppeal.setReceiverName(SysUtil.getCurrentUser().getRealName());
        }
        return appAssessAppealDao.update(appAssessAppeal);
    }

    public PageResult<AppAssessAppeal> queryPage(AppAssessAppeal appAssessAppeal,Page page) {
        PageResult<AppAssessAppeal> pageResult = new PageResult<>();
        pageResult.setDataList(appAssessAppealDao.queryPage(appAssessAppeal,page));
        pageResult.setTotal(appAssessAppealDao.queryPageCount(appAssessAppeal).intValue());
        return pageResult;
    }
}
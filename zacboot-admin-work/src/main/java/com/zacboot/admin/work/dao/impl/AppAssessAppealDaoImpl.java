package com.zacboot.admin.work.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cn.hutool.core.lang.UUID;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.util.SysUtil;
import java.util.List;
import com.zacboot.admin.work.beans.example.AppAssessAppealExample;
import com.zacboot.admin.work.dao.AppAssessAppealDao;
import com.zacboot.admin.work.mapper.AppAssessAppealMapper;

import com.zacboot.system.core.entity.assess.AppAssessAppeal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 * @date 2023年4月20日星期四
 * @author zac
 */
@Repository
@Slf4j
public class AppAssessAppealDaoImpl implements AppAssessAppealDao {
    @Autowired
    private AppAssessAppealMapper appAssessAppealMapper;

    public Integer add(AppAssessAppeal appAssessAppeal) {
        appAssessAppeal.setUuid(UUID.randomUUID().toString(true));
        appAssessAppeal.setCreateTime(LocalDateTime.now());
        appAssessAppeal.setCreateUser(SysUtil.getCurrentUser().getNickname());
        appAssessAppeal.setDeleted(false);
        return appAssessAppealMapper.insertSelective(appAssessAppeal);
    }

    public Integer del(AppAssessAppeal appAssessAppeal) {
        AppAssessAppealExample appAssessAppealExample = new AppAssessAppealExample();
        buildExample(appAssessAppeal,appAssessAppealExample);
        return appAssessAppealMapper.deleteByExample(appAssessAppealExample);
    }

    public Integer update(AppAssessAppeal appAssessAppeal) {
        AppAssessAppealExample appAssessAppealExample = new AppAssessAppealExample();
        buildExample(appAssessAppeal,appAssessAppealExample);
        return appAssessAppealMapper.updateByExampleSelective(appAssessAppeal,appAssessAppealExample);
    }

    public Page<AppAssessAppeal> queryPage(AppAssessAppeal appAssessAppeal, Page<AppAssessAppeal> page) {
        return appAssessAppealMapper.selectPage(page,new LambdaQueryWrapper<>());
    }

    public Long queryPageCount(AppAssessAppeal appAssessAppeal) {
        AppAssessAppealExample appAssessAppealExample = new AppAssessAppealExample();
        buildExample(appAssessAppeal,appAssessAppealExample);
        return appAssessAppealMapper.countByExample(appAssessAppealExample);
    }

    public AppAssessAppealExample buildExample(AppAssessAppeal appAssessAppeal, AppAssessAppealExample appAssessAppealExample) {
        AppAssessAppealExample.Criteria criteria = appAssessAppealExample.createCriteria();
        if (StringUtils.isNotBlank(appAssessAppeal.getUuid())){
            criteria.andUuidEqualTo(appAssessAppeal.getUuid());
        }
        return appAssessAppealExample;
    }
}
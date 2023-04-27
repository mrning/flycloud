package com.zacboot.admin.property.dao.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.admin.property.dao.AppPropertyDao;
import com.zacboot.admin.property.dto.example.AppPropertyExample;
import com.zacboot.admin.property.mapper.AppPropertyMapper;
import com.zacboot.system.core.entity.administration.AppProperty;
import com.zacboot.system.core.util.SysUtil;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 * @date 2023年4月26日星期三
 * @author zac
 */
@Repository
@Slf4j
public class AppPropertyDaoImpl implements AppPropertyDao {
    @Autowired
    private AppPropertyMapper appPropertyMapper;

    @Override
    public Integer add(AppProperty appProperty) {
        appProperty.setUuid(UUID.randomUUID().toString(true));
        appProperty.setCreateTime(LocalDateTime.now());
        appProperty.setCreateUser(SysUtil.getCurrentUser().getNickname());
        appProperty.setDeleted(false);
        return appPropertyMapper.insertSelective(appProperty);
    }

    @Override
    public Integer del(AppProperty appProperty) {
        AppPropertyExample appPropertyExample = new AppPropertyExample();
        buildExample(appProperty,appPropertyExample);
        return appPropertyMapper.deleteByExample(appPropertyExample);
    }

    @Override
    public Integer update(AppProperty appProperty) {
        AppPropertyExample appPropertyExample = new AppPropertyExample();
        buildExample(appProperty,appPropertyExample);
        return appPropertyMapper.updateByExampleSelective(appProperty,appPropertyExample);
    }

    @Override
    public Page<AppProperty> queryPage(AppProperty appProperty, Page<AppProperty> page) {
        return appPropertyMapper.selectPage(page,new LambdaQueryWrapper<>());
    }

    @Override
    public Long queryPageCount(AppProperty appProperty) {
        AppPropertyExample appPropertyExample = new AppPropertyExample();
        buildExample(appProperty,appPropertyExample);
        return appPropertyMapper.countByExample(appPropertyExample);
    }

    public AppPropertyExample buildExample(AppProperty appProperty, AppPropertyExample appPropertyExample) {
        AppPropertyExample.Criteria criteria = appPropertyExample.createCriteria();
        if (StringUtils.isNotBlank(appProperty.getUuid())){
            criteria.andUuidEqualTo(appProperty.getUuid());
        }
        return appPropertyExample;
    }
}
package com.zacboot.admin.work.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.admin.work.beans.example.AppUserTimeAssessExample;
import com.zacboot.admin.work.dao.AppUserTimeAssessDao;
import com.zacboot.admin.work.mapper.AppUserTimeAssessMapper;
import com.zacboot.system.core.entity.assess.AppUserMonthAssess;
import com.zacboot.system.core.entity.assess.AppUserTimeAssess;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
@Repository
@Slf4j
public class AppUserTimeAssessDaoImpl implements AppUserTimeAssessDao {
    @Autowired
    private AppUserTimeAssessMapper appUserTimeAssessMapper;

    public Integer add(AppUserTimeAssess appUserTimeAssess) {
        return appUserTimeAssessMapper.insertSelective(appUserTimeAssess);
    }

    public Integer del(AppUserTimeAssess appUserTimeAssess) {
        AppUserTimeAssessExample appUserTimeAssessExample = new AppUserTimeAssessExample();
        buildExample(appUserTimeAssess,appUserTimeAssessExample);
        return appUserTimeAssessMapper.deleteByExample(appUserTimeAssessExample);
    }

    public Integer update(AppUserTimeAssess appUserTimeAssess) {
        AppUserTimeAssessExample appUserTimeAssessExample = new AppUserTimeAssessExample();
        buildExample(appUserTimeAssess,appUserTimeAssessExample);
        return appUserTimeAssessMapper.updateByExampleSelective(appUserTimeAssess,appUserTimeAssessExample);
    }
    public Page<AppUserTimeAssess> queryPage(AppUserTimeAssess appUserTimeAssess, Page<AppUserTimeAssess> page) {
        return appUserTimeAssessMapper.selectPage(page,new LambdaQueryWrapper<>());
    }

    public Long queryPageCount(AppUserTimeAssess appUserTimeAssess) {
        AppUserTimeAssessExample appUserTimeAssessExample = new AppUserTimeAssessExample();
        buildExample(appUserTimeAssess,appUserTimeAssessExample);
        return appUserTimeAssessMapper.countByExample(appUserTimeAssessExample);
    }

    @Override
    public List<AppUserTimeAssess> queryByUserUuid(String userUuid) {
        return appUserTimeAssessMapper.selectList(new LambdaQueryWrapper<AppUserTimeAssess>()
                .eq(AppUserTimeAssess::getUserUuid,userUuid));
    }

    public AppUserTimeAssessExample buildExample(AppUserTimeAssess appUserTimeAssess, AppUserTimeAssessExample appUserTimeAssessExample) {
        AppUserTimeAssessExample.Criteria criteria = appUserTimeAssessExample.createCriteria();
        if (StringUtils.isNotBlank(appUserTimeAssess.getUuid())){
            criteria.andUuidEqualTo(appUserTimeAssess.getUuid());
        }
        return appUserTimeAssessExample;
    }

}
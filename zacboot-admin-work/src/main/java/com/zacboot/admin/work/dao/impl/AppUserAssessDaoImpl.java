package com.zacboot.admin.work.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.admin.work.beans.example.AppUserAssessExample;
import com.zacboot.admin.work.dao.AppUserAssessDao;
import com.zacboot.admin.work.mapper.AppUserAssessMapper;
import com.zacboot.system.core.entity.assess.AppUserAssess;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
@Repository
@Slf4j
public class AppUserAssessDaoImpl implements AppUserAssessDao {
    @Autowired
    private AppUserAssessMapper appUserAssessMapper;

    public Integer add(AppUserAssess appUserAssess) {
        return appUserAssessMapper.insertSelective(appUserAssess);
    }

    public Integer del(AppUserAssess appUserAssess) {
        AppUserAssessExample appUserAssessExample = new AppUserAssessExample();
        buildExample(appUserAssess,appUserAssessExample);
        return appUserAssessMapper.deleteByExample(appUserAssessExample);
    }

    public Integer update(AppUserAssess appUserAssess) {
        AppUserAssessExample appUserAssessExample = new AppUserAssessExample();
        buildExample(appUserAssess,appUserAssessExample);
        return appUserAssessMapper.updateByExampleSelective(appUserAssess,appUserAssessExample);
    }

    public Page<AppUserAssess> queryPage(AppUserAssess appUserAssess, Page<AppUserAssess> page) {
        return appUserAssessMapper.selectPage(page,new LambdaQueryWrapper<>());
    }

    public Long queryPageCount(AppUserAssess appUserAssess) {
        AppUserAssessExample appUserAssessExample = new AppUserAssessExample();
        buildExample(appUserAssess,appUserAssessExample);
        return appUserAssessMapper.countByExample(appUserAssessExample);
    }

    @Override
    public AppUserAssess queryByUserUuid(String userUuid) {
        return appUserAssessMapper.selectOne(new LambdaQueryWrapper<AppUserAssess>()
                .eq(AppUserAssess::getUserUuid,userUuid));
    }

    public AppUserAssessExample buildExample(AppUserAssess appUserAssess, AppUserAssessExample appUserAssessExample) {
        AppUserAssessExample.Criteria criteria = appUserAssessExample.createCriteria();
        return appUserAssessExample;
    }
}
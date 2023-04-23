package com.zacboot.admin.work.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.admin.work.beans.example.AppUserMonthAssessExample;
import com.zacboot.admin.work.dao.AppUserMonthAssessDao;
import java.util.List;

import com.zacboot.admin.work.mapper.AppUserMonthAssessMapper;
import com.zacboot.system.core.entity.assess.AppUserMonthAssess;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 * @date 2023年4月23日星期日
 * @author zac
 */
@Repository
@Slf4j
public class AppUserMonthAssessDaoImpl implements AppUserMonthAssessDao {
    @Autowired
    private AppUserMonthAssessMapper appUserMonthAssessMapper;

    public Integer add(AppUserMonthAssess appUserMonthAssess) {
        return appUserMonthAssessMapper.insertSelective(appUserMonthAssess);
    }

    public Integer del(AppUserMonthAssess appUserMonthAssess) {
        AppUserMonthAssessExample appUserMonthAssessExample = new AppUserMonthAssessExample();
        buildExample(appUserMonthAssess,appUserMonthAssessExample);
        return appUserMonthAssessMapper.deleteByExample(appUserMonthAssessExample);
    }

    public Integer update(AppUserMonthAssess appUserMonthAssess) {
        AppUserMonthAssessExample appUserMonthAssessExample = new AppUserMonthAssessExample();
        buildExample(appUserMonthAssess,appUserMonthAssessExample);
        return appUserMonthAssessMapper.updateByExampleSelective(appUserMonthAssess,appUserMonthAssessExample);
    }

    public Page<AppUserMonthAssess> queryPage(AppUserMonthAssess appUserMonthAssess, Page<AppUserMonthAssess> page) {
        return appUserMonthAssessMapper.selectPage(page,new LambdaQueryWrapper<>());
    }

    public Long queryPageCount(AppUserMonthAssess appUserMonthAssess) {
        AppUserMonthAssessExample appUserMonthAssessExample = new AppUserMonthAssessExample();
        buildExample(appUserMonthAssess,appUserMonthAssessExample);
        return appUserMonthAssessMapper.countByExample(appUserMonthAssessExample);
    }

    @Override
    public List<AppUserMonthAssess> queryByUserUuid(String userUuid) {
        return appUserMonthAssessMapper.selectList(new LambdaQueryWrapper<AppUserMonthAssess>()
                .eq(AppUserMonthAssess::getUserUuid,userUuid));
    }

    public AppUserMonthAssessExample buildExample(AppUserMonthAssess appUserMonthAssess, AppUserMonthAssessExample appUserMonthAssessExample) {
        AppUserMonthAssessExample.Criteria criteria = appUserMonthAssessExample.createCriteria();
        return appUserMonthAssessExample;
    }
}
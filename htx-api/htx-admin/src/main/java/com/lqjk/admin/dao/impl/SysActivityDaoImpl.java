package com.lqjk.admin.dao.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.time.LocalDateTime;

import com.lqjk.admin.beans.example.SysActivityExample;
import com.lqjk.admin.beans.vos.request.SysActivityPageRequest;
import com.lqjk.admin.dao.SysActivityDao;
import com.lqjk.admin.mapper.SysActivityMapper;
import com.lqjk.base.bizentity.SysActivity;
import com.lqjk.base.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 * @date 2023年10月30日星期一
 * @author zac
 */
@Repository
@Slf4j
public class SysActivityDaoImpl implements SysActivityDao {
    @Autowired
    private SysActivityMapper sysActivityMapper;

    @Override
    public Integer add(SysActivity sysActivity) {
        sysActivity.setUuid(UUID.randomUUID().toString(true));
        sysActivity.setCreateTime(LocalDateTime.now());
        sysActivity.setCreateUser(SysUtil.getCurrentUser().getNickname());
        sysActivity.setDeleted(false);
        return sysActivityMapper.insertSelective(sysActivity);
    }

    @Override
    public Integer del(SysActivity sysActivity) {
        SysActivityExample sysActivityExample = new SysActivityExample();
        buildExample(sysActivity,sysActivityExample);
        return sysActivityMapper.deleteByExample(sysActivityExample);
    }

    @Override
    public Integer update(SysActivity sysActivity) {
        SysActivityExample sysActivityExample = new SysActivityExample();
        sysActivity.setUpdateTime(LocalDateTime.now());
        sysActivity.setUpdateUser(SysUtil.getCurrentUser().getNickname());
        buildExample(sysActivity,sysActivityExample);
        return sysActivityMapper.updateByExampleSelective(sysActivity,sysActivityExample);
    }

    @Override
    public Page<SysActivity> queryPage(SysActivityPageRequest pageRequest) {
        return sysActivityMapper.selectPage(new Page<>(pageRequest.getPage(),pageRequest.getPageSize()),new LambdaQueryWrapper<>());
    }

    @Override
    public Long queryPageCount(SysActivity sysActivity) {
        SysActivityExample sysActivityExample = new SysActivityExample();
        buildExample(sysActivity,sysActivityExample);
        return sysActivityMapper.countByExample(sysActivityExample);
    }

    public SysActivityExample buildExample(SysActivity sysActivity, SysActivityExample sysActivityExample) {
        SysActivityExample.Criteria criteria = sysActivityExample.createCriteria();
        if (StringUtils.isNotBlank(sysActivity.getUuid())){
              criteria.andUuidEqualTo(sysActivity.getUuid());
        }
        return sysActivityExample;
    }
}
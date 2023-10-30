package com.lqjk.admin.dao.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lqjk.admin.beans.example.SysDictExample;
import com.lqjk.admin.dao.SysDictDao;
import com.lqjk.admin.mapper.SysDictMapper;
import com.lqjk.base.bizentity.SysDict;
import com.lqjk.base.utils.SysUtil;

import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 *
 * @author zac
 * @date 2023年4月26日星期三
 */
@Repository
@Slf4j
public class SysDictDaoImpl implements SysDictDao {
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public Integer add(SysDict sysDict) {
        sysDict.setUuid(UUID.randomUUID().toString(true));
        sysDict.setCreateTime(LocalDateTime.now());
        sysDict.setCreateUser(SysUtil.getCurrentUser().getNickname());
        sysDict.setDeleted(false);
        return sysDictMapper.insertSelective(sysDict);
    }

    @Override
    public Integer del(SysDict sysDict) {
        SysDictExample sysDictExample = new SysDictExample();
        buildExample(sysDict, sysDictExample);
        return sysDictMapper.deleteByExample(sysDictExample);
    }

    @Override
    public Integer update(SysDict sysDict) {
        SysDictExample sysDictExample = new SysDictExample();
        buildExample(sysDict, sysDictExample);
        return sysDictMapper.updateByExampleSelective(sysDict, sysDictExample);
    }

    @Override
    public Page<SysDict> queryPage(SysDict sysDict, Page<SysDict> page) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sysDict.getName()), SysDict::getName,sysDict.getName());
        queryWrapper.eq(StringUtils.isNotBlank(sysDict.getCode()), SysDict::getCode, sysDict.getCode());
        queryWrapper.orderByAsc(SysDict::getParentName, SysDict::getSortNo);
        return sysDictMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Long queryPageCount(SysDict sysDict) {
        SysDictExample sysDictExample = new SysDictExample();
        buildExample(sysDict, sysDictExample);
        return sysDictMapper.countByExample(sysDictExample);
    }

    @Override
    public List<SysDict> queryAll() {
        return sysDictMapper.selectAll();
    }

    @Override
    public List<SysDict> queryByParentUuid(String parentCode) {
        return sysDictMapper.selectList(Wrappers.lambdaQuery(SysDict.class).eq(SysDict::getParentCode, parentCode));
    }

    public SysDictExample buildExample(SysDict sysDict, SysDictExample sysDictExample) {
        SysDictExample.Criteria criteria = sysDictExample.createCriteria();
        if (StringUtils.isNotBlank(sysDict.getUuid())) {
            criteria.andUuidEqualTo(sysDict.getUuid());
        }
        return sysDictExample;
    }
}
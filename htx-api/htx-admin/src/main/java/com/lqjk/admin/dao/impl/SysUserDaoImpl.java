package com.lqjk.admin.dao.impl;

import cn.hutool.db.Page;
import com.lqjk.admin.entity.SysUser;
import com.lqjk.admin.beans.example.SysUserExample;
import com.lqjk.admin.dao.SysUserDao;
import com.lqjk.admin.mapper.SysUserMapper;
import com.lqjk.request.req.admin.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
@Repository
@Slf4j
public class SysUserDaoImpl implements SysUserDao {
    
    @Autowired
    private SysUserMapper sysUserMapper;

    public Integer add(SysUser sysUser) {
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setDeleted(false);
        return sysUserMapper.insertSelective(sysUser);
    }

    public Integer del(SysUser sysUser) {
        SysUserExample s = buildWhereParam(sysUser);
        sysUser.setDeleted(true);
        sysUser.setUpdateTime(LocalDateTime.now());
        return sysUserMapper.updateByExampleSelective(sysUser, s);
    }

    public Integer update(SysUser sysUser) {
        SysUserExample sysUserExample = buildWhereParam(sysUser);
        sysUser.setUpdateTime(LocalDateTime.now());
        return sysUserMapper.updateByExampleSelective(sysUser, sysUserExample);
    }

    public List<SysUser> queryPage(UserRequest userRequest, Page page) {
        SysUser sysUser = new SysUser();
        sysUser.setUuid(userRequest.getUserUuid());
        SysUserExample sysUserExample = buildWhereParam(sysUser);
        return sysUserMapper.selectByExampleWithRowbounds(sysUserExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(UserRequest userRequest) {
        SysUser sysUser = new SysUser();
        sysUser.setUuid(userRequest.getUserUuid());
        SysUserExample sysUserExample = buildWhereParam(sysUser);
        return sysUserMapper.countByExample(sysUserExample);
    }

    private SysUserExample buildWhereParam(SysUser sysUser) {
        SysUserExample sysUserExample = new SysUserExample();
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        if(StringUtils.isNotBlank(sysUser.getUuid())){
            criteria.andUuidEqualTo(sysUser.getUuid());
        }
        criteria.andDeletedEqualTo(Boolean.FALSE);
        return sysUserExample;
    }

    @Override
    public SysUser getUserByName(String username) {
        return sysUserMapper.getUserByName(username);
    }

    @Override
    public SysUser queryByUuid(String uuid) {
        return sysUserMapper.getUserByUuid(uuid);
    }
}
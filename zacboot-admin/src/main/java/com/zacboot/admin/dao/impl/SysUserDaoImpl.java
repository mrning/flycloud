package com.zacboot.admin.dao.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Page;
import com.zacboot.admin.beans.entity.SysUser;
import com.zacboot.admin.beans.example.SysUserExample;
import com.zacboot.admin.beans.vos.request.UserRequest;
import com.zacboot.admin.dao.SysUserDao;
import com.zacboot.admin.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        sysUser.setCreateTime(DateUtil.date());
        sysUser.setDeleted(false);
        return sysUserMapper.insertSelective(sysUser);
    }

    public Integer del(SysUser sysUser) {
        SysUserExample s = buildWhereParam(sysUser);
        sysUser.setDeleted(true);
        sysUser.setUpdateTime(DateUtil.date());
        return sysUserMapper.updateByExample(sysUser,s);
    }

    public Integer update(SysUser sysUser) {
        SysUserExample sysUserExample = buildWhereParam(sysUser);
        sysUser.setUpdateTime(DateUtil.date());
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
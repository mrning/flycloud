package com.zacboot.api.mini.dao.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zacboot.api.mini.beans.dtos.example.MiniSysUserExample;
import com.zacboot.api.mini.dao.MiniSysUserDao;
import com.zacboot.api.mini.mapper.MiniUserMapper;
import com.zacboot.system.core.entity.mini.MiniUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年3月19日星期日
 * @author zac
 */
@Repository
@Slf4j
public class MiniSysUserDaoImpl implements MiniSysUserDao {
    @Autowired
    private MiniUserMapper miniUserMapper;

    public Integer add(MiniUser miniUser) {
        miniUser.setCreateTime(LocalDateTime.now());
        miniUser.setDeleted(Boolean.FALSE);
        miniUser.setUuid(UUID.randomUUID(true).toString());
        return miniUserMapper.insertSelective(miniUser);
    }

    public Integer del(MiniUser miniUser) {
        MiniSysUserExample miniSysUserExample = new MiniSysUserExample();
        buildExample(miniUser,miniSysUserExample);
        miniUser.setDeleted(Boolean.TRUE);
        return miniUserMapper.updateByExampleSelective(miniUser,miniSysUserExample);
    }

    public Integer update(MiniUser miniUser) {
        MiniSysUserExample miniSysUserExample = new MiniSysUserExample();
        buildExample(miniUser,miniSysUserExample);
        miniUser.setUpdateTime(LocalDateTime.now());
        return miniUserMapper.updateByExampleSelective(miniUser,miniSysUserExample);
    }

    @Override
    public MiniUser queryByOpenId(String openId) {
        return miniUserMapper.selectOne(new LambdaQueryWrapper<MiniUser>().eq(MiniUser::getOpenId,openId));
    }

    public List<MiniUser> queryPage(MiniUser miniUser, Page page) {
        MiniSysUserExample miniSysUserExample = new MiniSysUserExample();
        buildExample(miniUser,miniSysUserExample);
        return miniUserMapper.selectByExampleWithRowbounds(miniSysUserExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(MiniUser miniUser) {
        MiniSysUserExample miniSysUserExample = new MiniSysUserExample();
        buildExample(miniUser,miniSysUserExample);
        return miniUserMapper.countByExample(miniSysUserExample);
    }

    public MiniSysUserExample buildExample(MiniUser miniUser, MiniSysUserExample miniSysUserExample) {
        MiniSysUserExample.Criteria criteria = miniSysUserExample.createCriteria();
        return miniSysUserExample;
    }
}
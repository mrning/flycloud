package com.zacboot.admin.dao.impl;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zacboot.admin.beans.entity.SysDept;
import com.zacboot.admin.beans.example.SysDeptExample;
import com.zacboot.admin.beans.vos.request.DeptRequest;
import com.zacboot.admin.dao.SysDeptDao;
import com.zacboot.admin.mapper.SysDeptMapper;
import com.zacboot.admin.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
@Repository
@Slf4j
public class SysDeptDaoImpl implements SysDeptDao {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    public Integer add(SysDept sysDept) {
        sysDept.setCreateTime(LocalDateTime.now());
        sysDept.setCreateUser(SysUtil.getCurrentUser().getNickname());
        sysDept.setDeleted(false);
        return sysDeptMapper.insertSelective(sysDept);
    }

    public Integer del(SysDept sysDept) {
        SysDeptExample s = new SysDeptExample();
        s.createCriteria().andUuidEqualTo(sysDept.getUuid());
        sysDept.setDeleted(true);
        sysDept.setUpdateTime(LocalDateTime.now());
        return sysDeptMapper.updateByExampleSelective(sysDept, s);
    }

    public Integer update(SysDept sysDept) {
        sysDept.setUpdateTime(LocalDateTime.now());
        sysDept.setUpdateUser(SysUtil.getCurrentUser().getNickname());
        SysDeptExample sysDeptExample = new SysDeptExample();
        return sysDeptMapper.updateByExampleSelective(sysDept, sysDeptExample);
    }

    public List<SysDept> queryPage(DeptRequest deptRequest, Page page) {
        SysDeptExample sysDeptExample = new SysDeptExample();
        sysDeptExample.createCriteria().andDeletedEqualTo(false);
        return sysDeptMapper.selectByExampleWithRowbounds(sysDeptExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(DeptRequest deptRequest) {
        SysDeptExample sysDeptExample = new SysDeptExample();
        return sysDeptMapper.countByExample(sysDeptExample);
    }

    @Override
    public List<SysDept> queryAll() {
        return sysDeptMapper.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public SysDept queryByUuid(String deptUuid) {
        SysDeptExample sysDeptExample = new SysDeptExample();
        sysDeptExample.createCriteria().andUuidEqualTo(deptUuid);
        return sysDeptMapper.selectByExample(sysDeptExample).get(0);
    }
}
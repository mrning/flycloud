package com.zac.admin.dao.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zac.admin.beans.example.SysDeptExample;
import com.zac.admin.beans.vos.request.DeptRequest;
import com.zac.admin.dao.SysDeptDao;
import com.zac.base.bizentity.SysDept;
import com.zac.admin.mapper.SysDeptMapper;
import com.zac.base.bizentity.SysDict;
import com.zac.base.bizentity.SysUser;
import com.zac.base.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        sysDept.setUuid(UUID.randomUUID().toString(true));
        sysDept.setCreateTime(LocalDateTime.now());
        sysDept.setCreateUser(SysUtil.getCurrentUser().getNickname());
        sysDept.setDeleted(false);
        return sysDeptMapper.insertSelective(sysDept);
    }

    public Integer del(SysDept sysDept) {
        sysDept.setDeleted(true);
        sysDept.setUpdateTime(LocalDateTime.now());

        return sysDeptMapper.updateByExampleSelective(sysDept, buildExample(sysDept));
    }

    public Integer update(SysDept sysDept) {
        sysDept.setUpdateTime(LocalDateTime.now());
        sysDept.setUpdateUser(SysUtil.getCurrentUser().getNickname());
        return sysDeptMapper.updateByExampleSelective(sysDept, buildExample(sysDept));
    }

    public List<SysDept> queryPage(DeptRequest deptRequest, Page page) {
        SysDept sysDept = SysDept.convertByRequest(deptRequest);
        return sysDeptMapper.selectByExampleWithRowbounds(buildExample(sysDept),new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(DeptRequest deptRequest) {
        SysDeptExample sysDeptExample = buildExample(SysDept.convertByRequest(deptRequest));
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

    public SysDeptExample buildExample(SysDept sysDept) {
        SysDeptExample sysDeptExample = new SysDeptExample();
        SysDeptExample.Criteria criteria = sysDeptExample.createCriteria();
        if (StringUtils.isNotBlank(sysDept.getUuid())){
            criteria.andUuidEqualTo(sysDept.getUuid());
        }
        if (StringUtils.isNotBlank(sysDept.getDepartName())){
            criteria.andDepartNameLike("%"+sysDept.getDepartName()+"%");
        }
        criteria.andDeletedEqualTo(Boolean.FALSE);
        return sysDeptExample;
    }
}
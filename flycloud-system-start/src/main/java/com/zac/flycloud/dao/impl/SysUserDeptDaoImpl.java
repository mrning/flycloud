package com.zac.flycloud.dao.impl;

import cn.hutool.db.Page;
import com.zac.flycloud.bean.dto.SysDept;
import com.zac.flycloud.bean.dto.SysUserDept;
import com.zac.flycloud.bean.dto.example.SysUserDeptExample;
import com.zac.flycloud.bean.vos.request.UserDeptRequest;
import com.zac.flycloud.dao.SysUserDeptDao;
import com.zac.flycloud.dao.mapper.SysUserDeptMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysUserDeptDaoImpl implements SysUserDeptDao {

    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

    public Integer add(SysUserDept sysUserDept) {
        return sysUserDeptMapper.insertSelective(sysUserDept);
    }

    public Integer del(SysUserDept sysUserDept) {
        SysUserDeptExample sysUserDeptExample = new SysUserDeptExample();
        return sysUserDeptMapper.deleteByExample(sysUserDeptExample);
    }

    public Integer update(SysUserDept sysUserDept) {
        SysUserDeptExample sysUserDeptExample = new SysUserDeptExample();
        return sysUserDeptMapper.updateByExampleSelective(sysUserDept, sysUserDeptExample);
    }

    public List<SysUserDept> queryPage(UserDeptRequest userDeptRequest, Page page) {
        SysUserDeptExample sysUserDeptExample = new SysUserDeptExample();
        return sysUserDeptMapper.selectByExampleWithRowbounds(sysUserDeptExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(UserDeptRequest userDeptRequest) {
        SysUserDeptExample sysUserDeptExample = new SysUserDeptExample();
        return sysUserDeptMapper.countByExample(sysUserDeptExample);
    }

    @Override
    public List<SysDept> getDeptsByUserUuid(String userUuid) {
        return sysUserDeptMapper.getDeptsByUserUuid(userUuid);
    }
}

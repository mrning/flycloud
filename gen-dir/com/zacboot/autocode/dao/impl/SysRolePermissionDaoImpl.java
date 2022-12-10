package com.zacboot.autocode.dao.impl;

import cn.hutool.db.Page;
import com.zacboot.autocode.dao.SysRolePermissionDao;
import com.zacboot.autocode.dto.SysRolePermission;
import com.zacboot.autocode.dto.example.SysRolePermissionExample;
import com.zacboot.autocode.mapper.SysRolePermissionMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 * @date 2022年11月3日星期四
 * @author zac
 */
@Repository
@Slf4j
public class SysRolePermissionDaoImpl implements SysRolePermissionDao {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    public Integer add(SysRolePermission sysRolePermission) {
        return sysRolePermissionMapper.insertSelective(sysRolePermission);
    }

    public Integer del(SysRolePermission sysRolePermission) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        return sysRolePermissionMapper.deleteByExample(sysRolePermissionExample);
    }

    public Integer update(SysRolePermission sysRolePermission) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        return sysRolePermissionMapper.updateByExampleSelective(sysRolePermission,sysRolePermissionExample);
    }

    public List<SysRolePermission> queryPage(SysRolePermission sysRolePermission, Page page) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        return sysRolePermissionMapper.selectByExampleWithRowbounds(sysRolePermissionExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(SysRolePermission sysRolePermission) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        return sysRolePermissionMapper.countByExample(sysRolePermissionExample);
    }
}
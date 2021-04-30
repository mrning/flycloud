package com.zac.flycloud.dao.impl;

import cn.hutool.db.Page;
import com.zac.flycloud.dao.SysUserRoleDao;
import com.zac.flycloud.dto.SysUserRoleDTO;
import com.zac.flycloud.dto.example.SysUserRoleDTOExample;
import com.zac.flycloud.mapper.SysUserRoleDTOMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
@Repository
@Slf4j
public class SysUserRoleDaoImpl implements SysUserRoleDao {
    @Autowired
    private SysUserRoleDTOMapper sysUserRoleMapper;

    public Integer add(SysUserRoleDTO sysUserRoleDTO) {
        return sysUserRoleMapper.insertSelective(sysUserRoleDTO);
    }

    public Integer del(SysUserRoleDTO sysUserRoleDTO) {
        SysUserRoleDTOExample sysUserRoleDTOExample = new SysUserRoleDTOExample();
        return sysUserRoleMapper.deleteByExample(sysUserRoleDTOExample);
    }

    public Integer update(SysUserRoleDTO sysUserRoleDTO) {
        SysUserRoleDTOExample sysUserRoleDTOExample = new SysUserRoleDTOExample();
        return sysUserRoleMapper.updateByExampleSelective(sysUserRoleDTO,sysUserRoleDTOExample);
    }

    public List<SysUserRoleDTO> queryPage(SysUserRoleDTO sysUserRoleDTO, Page page) {
        SysUserRoleDTOExample sysUserRoleDTOExample = new SysUserRoleDTOExample();
        return sysUserRoleMapper.selectByExampleWithRowbounds(sysUserRoleDTOExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(SysUserRoleDTO sysUserRoleDTO) {
        SysUserRoleDTOExample sysUserRoleDTOExample = new SysUserRoleDTOExample();
        return sysUserRoleMapper.countByExample(sysUserRoleDTOExample);
    }
}
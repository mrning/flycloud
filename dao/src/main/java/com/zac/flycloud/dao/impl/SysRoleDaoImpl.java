package com.zac.flycloud.dao.impl;

import cn.hutool.db.Page;
import com.zac.flycloud.dao.SysRoleDao;
import com.zac.flycloud.dto.SysRoleDTO;
import com.zac.flycloud.dto.example.SysRoleDTOExample;
import com.zac.flycloud.mapper.SysRoleDTOMapper;
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
public class SysRoleDaoImpl implements SysRoleDao {
    @Autowired
    private SysRoleDTOMapper sysRoleMapper;

    public Integer add(SysRoleDTO sysRoleDTO) {
        return sysRoleMapper.insertSelective(sysRoleDTO);
    }

    public Integer del(SysRoleDTO sysRoleDTO) {
        SysRoleDTOExample sysRoleDTOExample = new SysRoleDTOExample();
        return sysRoleMapper.deleteByExample(sysRoleDTOExample);
    }

    public Integer update(SysRoleDTO sysRoleDTO) {
        SysRoleDTOExample sysRoleDTOExample = new SysRoleDTOExample();
        return sysRoleMapper.updateByExampleSelective(sysRoleDTO,sysRoleDTOExample);
    }

    public List<SysRoleDTO> queryPage(SysRoleDTO sysRoleDTO, Page page) {
        SysRoleDTOExample sysRoleDTOExample = new SysRoleDTOExample();
        return sysRoleMapper.selectByExampleWithRowbounds(sysRoleDTOExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(SysRoleDTO sysRoleDTO) {
        SysRoleDTOExample sysRoleDTOExample = new SysRoleDTOExample();
        return sysRoleMapper.countByExample(sysRoleDTOExample);
    }
}
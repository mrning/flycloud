package com.zac.flycloud.dao.impl;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zac.flycloud.bean.dto.SysRoleDTO;
import com.zac.flycloud.bean.dto.example.SysRoleDTOExample;
import com.zac.flycloud.bean.vos.RoleRequestVO;
import com.zac.flycloud.dao.SysRoleDao;
import com.zac.flycloud.dao.mapper.SysRoleDTOMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<SysRoleDTO> queryPage(RoleRequestVO roleRequestVO, Page page) {
        SysRoleDTOExample sysRoleDTOExample = new SysRoleDTOExample();
        return sysRoleMapper.selectByExampleWithRowbounds(sysRoleDTOExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(RoleRequestVO roleRequestVO) {
        SysRoleDTOExample sysRoleDTOExample = new SysRoleDTOExample();
        return sysRoleMapper.countByExample(sysRoleDTOExample);
    }

    @Override
    public List<SysRoleDTO> queryAll() {
        return sysRoleMapper.selectList(Wrappers.emptyWrapper());
    }
}
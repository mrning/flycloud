package com.zac.flycloud.dao.impl;

import cn.hutool.db.Page;
import com.zac.flycloud.dao.SysUserRoleDao;
import com.zac.flycloud.bean.tablemodel.SysUserRoleDTO;
import com.zac.flycloud.bean.dto.example.SysUserRoleExample;
import com.zac.flycloud.dao.mapper.SysUserRoleDTOMapper;
import java.util.List;

import com.zac.flycloud.bean.vos.UserRoleRequestVO;
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
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.deleteByExample(sysUserRoleExample);
    }

    public Integer update(SysUserRoleDTO sysUserRoleDTO) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.updateByExampleSelective(sysUserRoleDTO, sysUserRoleExample);
    }

    public List<SysUserRoleDTO> queryPage(UserRoleRequestVO userRoleRequestVO, Page page) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.selectByExampleWithRowbounds(sysUserRoleExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(UserRoleRequestVO userRoleRequestVO) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.countByExample(sysUserRoleExample);
    }
}
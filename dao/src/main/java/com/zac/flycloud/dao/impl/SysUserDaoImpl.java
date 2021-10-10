package com.zac.flycloud.dao.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Page;
import com.zac.flycloud.dao.SysUserDao;
import com.zac.flycloud.dto.SysUserDTO;
import com.zac.flycloud.dto.example.SysUserDTOExample;
import com.zac.flycloud.mapper.SysUserDTOMapper;
import java.util.List;

import com.zac.flycloud.vos.UserRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
@Repository
@Slf4j
public class SysUserDaoImpl implements SysUserDao {
    @Autowired
    private SysUserDTOMapper sysUserMapper;

    public Integer add(SysUserDTO sysUserDTO) {
        sysUserDTO.setCreateTime(DateUtil.date());
        sysUserDTO.setCreateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysUserMapper.insertSelective(sysUserDTO);
    }

    public Integer del(SysUserDTO sysUserDTO) {
        SysUserDTOExample s = buildWhereParam(sysUserDTO);
        sysUserDTO.setDeleted(true);
        sysUserDTO.setUpdateTime(DateUtil.date());
        sysUserDTO.setUpdateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysUserMapper.deleteByExample(s);
    }

    public Integer update(SysUserDTO sysUserDTO) {
        SysUserDTOExample sysUserDTOExample = buildWhereParam(sysUserDTO);
        sysUserDTO.setUpdateTime(DateUtil.date());
        sysUserDTO.setUpdateUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return sysUserMapper.updateByExampleSelective(sysUserDTO,sysUserDTOExample);
    }

    public List<SysUserDTO> queryPage(UserRequestVO userRequestVO, Page page) {
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setUuid(userRequestVO.getUserUuid());
        SysUserDTOExample sysUserDTOExample = buildWhereParam(sysUserDTO);
        return sysUserMapper.selectByExampleWithRowbounds(sysUserDTOExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(UserRequestVO userRequestVO) {
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setUuid(userRequestVO.getUserUuid());
        SysUserDTOExample sysUserDTOExample = buildWhereParam(sysUserDTO);
        return sysUserMapper.countByExample(sysUserDTOExample);
    }

    private SysUserDTOExample buildWhereParam(SysUserDTO sysUserDTO) {
        SysUserDTOExample sysUserDTOExample = new SysUserDTOExample();
        SysUserDTOExample.Criteria criteria = sysUserDTOExample.createCriteria();
        if(StringUtils.isNotBlank(sysUserDTO.getUuid())){
            criteria.andUuidEqualTo(sysUserDTO.getUuid());
        }
        return sysUserDTOExample;
    }
}
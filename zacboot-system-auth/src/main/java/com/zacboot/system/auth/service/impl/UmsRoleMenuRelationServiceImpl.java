package com.zacboot.system.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zacboot.system.auth.domain.UmsRoleMenuRelation;
import com.zacboot.system.auth.mapper.UmsRoleMenuRelationMapper;
import com.zacboot.system.auth.service.UmsRoleMenuRelationService;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关系管理Service实现类
 * Created by macro on 2020/8/21.
 */
@Service
public class UmsRoleMenuRelationServiceImpl extends ServiceImpl<UmsRoleMenuRelationMapper, UmsRoleMenuRelation> implements UmsRoleMenuRelationService {
}

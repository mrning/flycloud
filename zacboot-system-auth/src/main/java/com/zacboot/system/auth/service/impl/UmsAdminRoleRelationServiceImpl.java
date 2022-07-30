package com.zacboot.system.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zacboot.system.auth.domain.UmsAdminRoleRelation;
import com.zacboot.system.auth.mapper.UmsAdminRoleRelationMapper;
import com.zacboot.system.auth.service.UmsAdminRoleRelationService;
import org.springframework.stereotype.Service;

/**
 * 管理员角色关系管理Service实现类
 * Created by macro on 2020/8/21.
 */
@Service
public class UmsAdminRoleRelationServiceImpl extends ServiceImpl<UmsAdminRoleRelationMapper, UmsAdminRoleRelation> implements UmsAdminRoleRelationService {
}

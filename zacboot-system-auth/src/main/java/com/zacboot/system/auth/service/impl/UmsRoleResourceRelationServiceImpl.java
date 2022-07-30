package com.zacboot.system.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zacboot.system.auth.domain.UmsRoleResourceRelation;
import com.zacboot.system.auth.mapper.UmsRoleResourceRelationMapper;
import com.zacboot.system.auth.service.UmsRoleResourceRelationService;
import org.springframework.stereotype.Service;

/**
 * 角色资源关系管理Service实现类
 * Created by macro on 2020/8/21.
 */
@Service
public class UmsRoleResourceRelationServiceImpl extends ServiceImpl<UmsRoleResourceRelationMapper, UmsRoleResourceRelation> implements UmsRoleResourceRelationService {
}
